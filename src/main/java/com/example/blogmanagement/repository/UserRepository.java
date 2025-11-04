package com.example.blogmanagement.repository;

import com.example.blogmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for CRUD operations on User entities. Uses Spring Data JPA
 * to automatically implement standard CRUD operations. Additional query methods
 * allow lookup by email or username.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by email address.
     *
     * @param email email to search for
     * @return optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Check whether a user with the given email exists.
     *
     * @param email email to check
     * @return true if a user exists with that email, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Check whether a user with the given username exists.
     *
     * @param username username to check
     * @return true if a user exists with that username, false otherwise
     */
    boolean existsByUsername(String username);
}