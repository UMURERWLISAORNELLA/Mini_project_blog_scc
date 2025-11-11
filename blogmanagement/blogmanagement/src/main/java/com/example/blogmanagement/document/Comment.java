package com.example.blogmanagement.document;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * MongoDB document representing a comment on a blog post. Comments are stored
 * in a separate collection and reference both the associated post and the
 * authoring user by their identifiers.
 */
@Document(collection = "comments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    /**
     * Unique identifier for the comment. Assigned by MongoDB.
     */
    @Id
    private String id;

    /**
     * Identifier of the post this comment belongs to. This should match the
     * id of a Post document.
     */
    private String postId;

    /**
     * Identifier of the user who wrote the comment. Corresponds to a user
     * record stored in PostgreSQL.
     */
    private Long authorId;

    /**
     * Text content of the comment.
     */
    private String content;

    /**
     * Timestamp when the comment was created.
     */
    @CreatedDate
    private LocalDateTime createdAt;

    /**
     * Timestamp when the comment was last updated.
     */
    @LastModifiedDate
    private LocalDateTime updatedAt;
}