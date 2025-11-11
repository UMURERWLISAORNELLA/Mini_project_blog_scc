package com.example.blogmanagement.service;

import com.example.blogmanagement.dto.UserRegistrationRequest;
import com.example.blogmanagement.dto.UserResponseDto;

/**
 * Service interface for managing users. Provides methods for registering
 * new users and retrieving user details.
 */
public interface UserService {

    /**
     * Register a new user using the supplied registration request. Validation
     * should be performed prior to calling this method.
     *
     * @param request DTO containing user registration details
     * @return DTO representing the newly created user
     */
    UserResponseDto registerUser(UserRegistrationRequest request);

    /**
     * Retrieve a user by its identifier.
     *
     * @param userId the ID of the user to fetch
     * @return the user response DTO
     */
    UserResponseDto getUserById(Long userId);

    /**
     * Delete a user by its identifier. Only accessible by admins.
     *
     * @param userId the ID of the user to delete
     */
    void deleteUser(Long userId);
}