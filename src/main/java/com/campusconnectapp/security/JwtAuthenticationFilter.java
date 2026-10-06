package com.campusconnectapp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService userDetailsService) {

        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader =
                request.getHeader("Authorization");

        if (authorizationHeader == null ||
                !authorizationHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);

        try {

            String username =
                    jwtService.extractUsername(token);

            if (SecurityContextHolder.getContext()
                    .getAuthentication() == null) {

                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(username);

                if (!userDetails.isEnabled()) {
                    sendErrorResponse(
                            response,
                            "Account is disabled"
                    );
                    return;
                }
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authentication.setDetails(
                        new org.springframework.security.web.authentication
                                .WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }

        } catch (ExpiredJwtException e) {

            sendErrorResponse(
                    response,
                    "JWT token has expired"
            );
            return;

        } catch (MalformedJwtException e) {

            sendErrorResponse(
                    response,
                    "JWT token is malformed"
            );
            return;

        } catch (SignatureException e) {

            sendErrorResponse(
                    response,
                    "JWT signature is invalid"
            );
            return;

        } catch (UsernameNotFoundException e) {

            sendErrorResponse(
                    response,
                    "User not found with the provied email in jwt"
            );
            return;

        } catch (JwtException | IllegalArgumentException e) {

            sendErrorResponse(
                    response,
                    "Invalid JWT token(maybe jwt format could be wrong or null value was sent instead of jwt)"
            );
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(
            HttpServletResponse response,
            String message) throws IOException {

        SecurityContextHolder.clearContext();

        response.setStatus(
                HttpServletResponse.SC_UNAUTHORIZED
        );

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write("""
                {
                    "status": 401,
                    "message": "%s"
                }
                """.formatted(message));
    }
}