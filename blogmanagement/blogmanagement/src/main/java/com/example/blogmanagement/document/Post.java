package com.example.blogmanagement.document;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * MongoDB document representing a blog post. Each post stores its title,
 * content and the ID of the user who authored it. Creation and update
 * timestamps are maintained automatically by Spring Data MongoDB.
 */
@Document(collection = "posts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    /**
     * Unique identifier for the post. MongoDB will generate this value.
     */
    @Id
    private String id;

    /**
     * Title of the blog post.
     */
    private String title;

    /**
     * Main content of the blog post.
     */
    private String content;

    /**
     * Identifier of the user who created the post. This references the user
     * stored in PostgreSQL. Using a primitive type avoids storing nested
     * documents or DBRef.
     */
    private Long authorId;

    /**
     * Timestamp when the post was first created.
     */
    @CreatedDate
    private LocalDateTime createdAt;

    /**
     * Timestamp when the post was last updated.
     */
    @LastModifiedDate
    private LocalDateTime updatedAt;
}