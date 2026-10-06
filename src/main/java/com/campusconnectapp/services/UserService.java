package com.campusconnectapp.services;
import com.campusconnectapp.dtos.userDtos.ChangePasswordRequestDto;
import com.campusconnectapp.dtos.userDtos.UpdateUserRequestDto;
import com.campusconnectapp.dtos.userDtos.UpdateUserResponseDto;
import com.campusconnectapp.exceptions.userExceptions.DepartmentNotFoundException;
import com.campusconnectapp.exceptions.userExceptions.EmailAlreadyExistsException;
import com.campusconnectapp.exceptions.userExceptions.IncorrectOldPasswordException;
import com.campusconnectapp.exceptions.userExceptions.UserNotFoundException;
import com.campusconnectapp.mappers.userMappers.UserResponseMapper;
import com.campusconnectapp.models.*;
import com.campusconnectapp.repositories.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.campusconnectapp.dtos.userDtos.GetUserResponseDto;
import java.util.List;

import static com.campusconnectapp.utils.UserRoleUtil.determineRole;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserResponseMapper userResponseMapper;
    private final LostFoundPostRepository lostFoundPostRepository;
    private final PostImageRepository postImageRepository;
    private final MatchCandidateRepository matchCandidateRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            DepartmentRepository departmentRepository,
            PasswordEncoder passwordEncoder,
            UserResponseMapper userResponseMapper,
            LostFoundPostRepository lostFoundPostRepository,
            PostImageRepository postImageRepository,
            MatchCandidateRepository matchCandidateRepository,
            MessageRepository messageRepository,
            ConversationRepository conversationRepository ) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.departmentRepository = departmentRepository;
        this.passwordEncoder = passwordEncoder;
        this.userResponseMapper= userResponseMapper;
        this.conversationRepository=conversationRepository;
        this.lostFoundPostRepository=lostFoundPostRepository;
        this.postImageRepository=postImageRepository;
        this.messageRepository=messageRepository;
        this.matchCandidateRepository=matchCandidateRepository;
    }

    @Transactional
    public void createUser(User user, Long departmentId) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        String roleName = determineRole(user.getEmail());

        Role role = roleRepository.findByName(roleName);

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() ->
                        new DepartmentNotFoundException(departmentId)
                );
        user.setRole(role);
        user.setDepartment(department);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

         userRepository.save(user);
    }
    @Transactional(readOnly = true)
    public List<GetUserResponseDto> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(userResponseMapper::toGetUserResponseDto)
                .toList();
    }
    @Transactional(readOnly = true)
    public List<GetUserResponseDto> getUsersByName(String name) {

        return userRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(userResponseMapper::toGetUserResponseDto)
                .toList();
    }
    @Transactional
    public void deactivateUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setIsActive(false);

        userRepository.save(user);
    }
    @Transactional
    public void activateUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setIsActive(true);

        userRepository.save(user);
    }
    @Transactional(readOnly = true)
    public GetUserResponseDto getUserByEmail(String email) {

        determineRole(email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        return userResponseMapper.toGetUserResponseDto(user);
    }
    @Transactional
    public UpdateUserResponseDto updateUser(
            Long userId,
            UpdateUserRequestDto request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new DepartmentNotFoundException(request.getDepartmentId()));

        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setDepartment(department);

        User updatedUser = userRepository.save(user);

        return userResponseMapper.toUpdateUserResponseDto(updatedUser);
    }
    @Transactional
    public void changePassword(
            Long userId,
            ChangePasswordRequestDto request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (!passwordEncoder.matches(
                request.getOldPassword(),
                user.getPassword())) {

            throw new IncorrectOldPasswordException();
        }

        user.setPassword(
                passwordEncoder.encode(request.getNewPassword())
        );

        userRepository.save(user);
    }
    @Transactional
    public void deleteUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        //posts created by the user
        List<LostFoundPost> posts =
                lostFoundPostRepository.findByUserId(userId);

        List<Long> postIds = posts.stream()
                .map(LostFoundPost::getId)//post -> post.getId()
                .toList();

        List<Conversation> conversations =
                conversationRepository
                        .findByMatchCandidateLostPostIdInOrMatchCandidateFoundPostIdIn(
                                postIds, postIds
                        );
        List<Long> conversationIds = conversations.stream()
                .map(Conversation::getId)
                .toList();

        if (!conversationIds.isEmpty()) {
            messageRepository.deleteByConversationIdIn(conversationIds);
            conversationRepository.deleteAllByIdInBatch(conversationIds);
        }

        if (!postIds.isEmpty()) {
            matchCandidateRepository.deleteByLostPostIdInOrFoundPostIdIn(
                    postIds,
                    postIds
            );
        }
        if (!postIds.isEmpty()) {
            postImageRepository.deleteByPostIdIn(postIds);
        }

        if (!postIds.isEmpty()) {
            lostFoundPostRepository.deleteByUserId(userId);
        }

        userRepository.delete(user);

    }
}