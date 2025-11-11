package com.example.blogmanagement.service.impl;

import com.example.blogmanagement.dto.UserRegistrationRequest;
import com.example.blogmanagement.dto.UserResponseDto;
import com.example.blogmanagement.entity.Role;
import com.example.blogmanagement.entity.User;
import com.example.blogmanagement.exception.ResourceAlreadyExistsException;
import com.example.blogmanagement.exception.ResourceNotFoundException;
import com.example.blogmanagement.repository.UserRepository;
import com.example.blogmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Default implementation of the {@link UserService}. Handles user registration
 * and retrieval using Spring Data JPA.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto registerUser(UserRegistrationRequest request) {
        // Check if a user with the given email or username already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email is already registered");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResourceAlreadyExistsException("Username is already taken");
        }

        // Map the request DTO to a User entity. Passwords are hashed using
        // BCrypt to avoid storing them in plaintext.
        // Assign USER role by default
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();

        User saved = userRepository.save(user);

        // Map the saved entity back to a response DTO excluding the password
        return UserResponseDto.builder()
                .id(saved.getId())
                .username(saved.getUsername())
                .email(saved.getEmail())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
    }
}