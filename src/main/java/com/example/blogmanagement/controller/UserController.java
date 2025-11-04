package com.example.blogmanagement.controller;

import com.example.blogmanagement.dto.UserRegistrationRequest;
import com.example.blogmanagement.dto.UserResponseDto;
import com.example.blogmanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller responsible for handling user registration and retrieval. Users
 * are stored in PostgreSQL. Sensitive fields like passwords are not returned
 * in responses.
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Operations on user accounts")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Register a new user. The request body must include a username, email and
     * password. Validation errors will be handled by the global exception
     * handler. On success, a 201 response containing the new user's details
     * (without the password) is returned.
     */
    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        UserResponseDto response = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieve a user by ID. Returns a 404 if the user does not exist.
     */
    @Operation(summary = "Get user by ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}