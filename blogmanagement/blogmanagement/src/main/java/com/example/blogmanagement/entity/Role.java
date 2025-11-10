package com.example.blogmanagement.entity;

/**
 * Enumeration representing the roles that users can have in the system.
 * Used for role-based access control (RBAC).
 */
public enum Role {
    /**
     * Regular user role. Users with this role can create, view, update,
     * and delete their own posts and comments.
     */
    USER,

    /**
     * Administrator role. Users with this role have full access to the system,
     * including the ability to delete any user, post, or comment regardless
     * of ownership.
     */
    ADMIN
}
