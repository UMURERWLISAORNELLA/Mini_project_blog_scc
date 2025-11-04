package com.example.blogmanagement.controller;

import com.example.blogmanagement.dto.PagedResponse;
import com.example.blogmanagement.dto.PostRequestDto;
import com.example.blogmanagement.dto.PostResponseDto;
import com.example.blogmanagement.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing blog posts. Supports creating, retrieving,
 * updating, deleting and searching posts. Posts are stored in MongoDB.
 */
@RestController
@RequestMapping("/api/posts")
@Tag(name = "Posts", description = "Operations on blog posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "Create a new post")
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostRequestDto request) {
        PostResponseDto response = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get a post by ID")
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable("id") String id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @Operation(summary = "Update a post")
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable("id") String id,
                                                     @Valid @RequestBody PostRequestDto request) {
        return ResponseEntity.ok(postService.updatePost(id, request));
    }

    @Operation(summary = "Delete a post")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") String id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "List posts with optional search and pagination")
    @GetMapping
    public ResponseEntity<PagedResponse<PostResponseDto>> listPosts(
            @Parameter(description = "Zero-based page index", in = ParameterIn.QUERY, schema = @Schema(defaultValue = "0"))
            @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of records per page", in = ParameterIn.QUERY, schema = @Schema(defaultValue = "10"))
            @RequestParam(value = "size", defaultValue = "10") int size,
            @Parameter(description = "Search term to filter posts by title or content", in = ParameterIn.QUERY)
            @RequestParam(value = "search", required = false) String search) {
        PagedResponse<PostResponseDto> response = postService.listPosts(page, size, search);
        return ResponseEntity.ok(response);
    }
}