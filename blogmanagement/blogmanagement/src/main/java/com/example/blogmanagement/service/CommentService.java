package com.example.blogmanagement.service;

import com.example.blogmanagement.dto.CommentRequestDto;
import com.example.blogmanagement.dto.CommentResponseDto;
import com.example.blogmanagement.dto.PagedResponse;

/**
 * Service interface for managing comments on blog posts.
 */
public interface CommentService {

    /**
     * Create a new comment on a post.
     *
     * @param request DTO containing post ID, author ID and content
     * @return response DTO for the created comment
     */
    CommentResponseDto createComment(CommentRequestDto request);

    /**
     * Retrieve a comment by its identifier.
     *
     * @param commentId unique identifier of the comment
     * @return response DTO representing the comment
     */
    CommentResponseDto getComment(String commentId);

    /**
     * Update an existing comment.
     *
     * @param commentId identifier of the comment to update
     * @param request DTO containing new content and author/post information
     * @return response DTO of the updated comment
     */
    CommentResponseDto updateComment(String commentId, CommentRequestDto request);

    /**
     * Delete a comment by its identifier.
     *
     * @param commentId identifier of the comment to delete
     */
    void deleteComment(String commentId);

    /**
     * List comments belonging to a specific post with pagination.
     *
     * @param postId identifier of the post whose comments should be retrieved
     * @param page zero-based page index
     * @param size page size
     * @return paginated response containing comment DTOs
     */
    PagedResponse<CommentResponseDto> listCommentsByPostId(String postId, int page, int size);
}