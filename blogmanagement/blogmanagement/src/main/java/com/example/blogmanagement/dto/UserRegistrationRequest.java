package com.example.blogmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object used when registering a new user. Validation annotations
 * ensure that the incoming request contains the required fields and that the
 * email is properly formatted.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {

    @Schema(description = "Unique username to identify the user", example = "johndoe")
    @NotBlank(message = "Username is required")
    private String username;

    @Schema(description = "User's email address", example = "john.doe@example.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(description = "User's plain text password", example = "password123")
    @NotBlank(message = "Password is required")
    private String password;
}