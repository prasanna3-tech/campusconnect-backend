package com.campusconnectapp.repositories;

import com.campusconnectapp.models.DeletedAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletedAccountRepository
        extends JpaRepository<DeletedAccount, Long> {
}