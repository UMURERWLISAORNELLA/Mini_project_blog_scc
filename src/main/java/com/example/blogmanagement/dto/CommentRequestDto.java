package com.example.blogmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO used when creating or updating a comment. Validation ensures that
 * the necessary identifiers and text content are provided.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {

    @Schema(description = "Identifier of the post being commented on", example = "64fe28c1f1a3ab12ef4c901e")
    @NotBlank(message = "Post ID is required")
    private String postId;

    @Schema(description = "Identifier of the user creating the comment", example = "1")
    @NotNull(message = "Author ID is required")
    private Long authorId;

    @Schema(description = "Text content of the comment", example = "Great article!")
    @NotBlank(message = "Content is required")
    private String content;
}