package com.example.blogmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Entity representing a registered user. This entity is persisted to
 * PostgreSQL via Spring Data JPA. Users are identified by a generated
 * primary key and contain basic details such as username, email and
 * password. Audit fields track when a user was created or updated.
 */
@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * Primary key for the user. Generated automatically by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Display name chosen by the user. Must be unique.
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * Email address associated with the user. Must be unique.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Hashed password for authentication. Stored securely and not exposed via DTOs.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Timestamp indicating when the user was created.
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating the last time the user record was updated.
     */
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}