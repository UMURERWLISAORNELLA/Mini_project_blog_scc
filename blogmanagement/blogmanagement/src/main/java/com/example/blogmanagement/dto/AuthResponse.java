package com.example.blogmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Data Transfer Object for authentication responses containing JWT token
 * and user information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    /**
     * JWT access token.
     */
    private String accessToken;

    /**
     * Token type (typically "Bearer").
     */
    @Builder.Default
    private String tokenType = "Bearer";

    /**
     * ID of the authenticated user.
     */
    private Long id;

    /**
     * Username of the authenticated user.
     */
    private String username;

    /**
     * Email of the authenticated user.
     */
    private String email;

    /**
     * Roles assigned to the authenticated user.
     */
    private Set<String> roles;
}
