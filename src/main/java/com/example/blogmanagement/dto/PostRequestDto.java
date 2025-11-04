package com.example.blogmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO used when creating or updating a blog post. Validation annotations
 * enforce the presence of required fields and limit the length of text inputs.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {

    @Schema(description = "Title of the post", example = "My first blog post")
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @Schema(description = "Content of the post", example = "This is my first post...")
    @NotBlank(message = "Content is required")
    private String content;

    @Schema(description = "Identifier of the user creating the post", example = "1")
    @NotNull(message = "Author ID is required")
    private Long authorId;
}