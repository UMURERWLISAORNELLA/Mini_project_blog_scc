package com.example.blogmanagement.controller;

import com.example.blogmanagement.dto.PagedResponse;
import com.example.blogmanagement.dto.PostRequestDto;
import com.example.blogmanagement.dto.PostResponseDto;
import com.example.blogmanagement.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Operation(summary = "Create a new post", description = "Create a new blog post. Requires authentication.")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostRequestDto request) {
        PostResponseDto response = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get a post by ID", description = "Retrieve a single blog post by its ID. Public endpoint.")
    @SecurityRequirements() // No security required
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable("id") String id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @Operation(summary = "Update a post", description = "Update an existing post. Users can only update their own posts. Admins can update any post.")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable("id") String id,
                                                     @Valid @RequestBody PostRequestDto request) {
        return ResponseEntity.ok(postService.updatePost(id, request));
    }

    @Operation(summary = "Delete a post", description = "Delete a post. Users can only delete their own posts. Admins can delete any post.")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deletePost(@PathVariable("id") String id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "List posts with optional search and pagination", description = "Get a paginated list of posts. Public endpoint.")
    @SecurityRequirements() // No security required
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