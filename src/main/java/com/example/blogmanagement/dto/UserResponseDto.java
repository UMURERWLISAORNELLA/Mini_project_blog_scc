package com.example.blogmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data transfer object returned after user registration or retrieval.
 * Passwords are intentionally excluded to avoid leaking sensitive information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    @Schema(description = "User identifier", example = "1")
    private Long id;

    @Schema(description = "Unique username", example = "johndoe")
    private String username;

    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Timestamp when the user was created")
    private LocalDateTime createdAt;
    // In a real‑world API we omit the updatedAt field from the response to
    // avoid leaking unnecessary internal details.
}