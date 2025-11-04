package com.example.blogmanagement.service;

import com.example.blogmanagement.dto.PagedResponse;
import com.example.blogmanagement.dto.PostRequestDto;
import com.example.blogmanagement.dto.PostResponseDto;

/**
 * Service interface for managing blog posts stored in MongoDB.
 */
public interface PostService {

    /**
     * Create a new blog post.
     *
     * @param request the post request DTO containing title, content and author
     * @return response DTO representing the created post
     */
    PostResponseDto createPost(PostRequestDto request);

    /**
     * Retrieve a single post by its identifier.
     *
     * @param postId unique identifier of the post
     * @return the corresponding response DTO
     */
    PostResponseDto getPost(String postId);

    /**
     * Update an existing post.
     *
     * @param postId unique identifier of the post to update
     * @param request DTO containing new title, content and optionally author
     * @return response DTO of the updated post
     */
    PostResponseDto updatePost(String postId, PostRequestDto request);

    /**
     * Delete a post and cascade deletion to its comments.
     *
     * @param postId unique identifier of the post to delete
     */
    void deletePost(String postId);

    /**
     * Retrieve a paginated list of posts, optionally filtering by a search keyword.
     *
     * @param page zero-based page index
     * @param size page size
     * @param search optional search term for title or content
     * @return a paged response containing the posts
     */
    PagedResponse<PostResponseDto> listPosts(int page, int size, String search);
}