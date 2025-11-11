package com.example.blogmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Response DTO representing a comment returned to the client. It includes
 * timestamps and references to both the post and the author.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

    @Schema(description = "Unique identifier of the comment")
    private String id;

    @Schema(description = "Identifier of the post that this comment belongs to")
    private String postId;

    @Schema(description = "Username of the user who wrote the comment")
    private String authorUsername;

    @Schema(description = "Content of the comment")
    private String content;

    @Schema(description = "Timestamp when the comment was created")
    private LocalDateTime createdAt;
    // updatedAt is omitted for a more realistic response DTO
}