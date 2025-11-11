package com.example.blogmanagement.controller;

import com.example.blogmanagement.dto.CommentRequestDto;
import com.example.blogmanagement.dto.CommentResponseDto;
import com.example.blogmanagement.dto.PagedResponse;
import com.example.blogmanagement.service.CommentService;
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
 * REST controller for managing comments on blog posts. Supports standard CRUD
 * operations as well as listing comments for a specific post with pagination.
 */
@RestController
@Tag(name = "Comments", description = "Operations on comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Create a new comment", description = "Create a new comment on a blog post. Requires authentication.")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/api/comments")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CommentResponseDto> createComment(@Valid @RequestBody CommentRequestDto request) {
        CommentResponseDto response = commentService.createComment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get a comment by ID", description = "Retrieve a single comment by its ID. Public endpoint.")
    @SecurityRequirements() // No security required
    @GetMapping("/api/comments/{id}")
    public ResponseEntity<CommentResponseDto> getComment(@PathVariable("id") String id) {
        return ResponseEntity.ok(commentService.getComment(id));
    }

    @Operation(summary = "Update a comment", description = "Update an existing comment. Users can only update their own comments. Admins can update any comment.")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/api/comments/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable("id") String id,
                                                           @Valid @RequestBody CommentRequestDto request) {
        return ResponseEntity.ok(commentService.updateComment(id, request));
    }

    @Operation(summary = "Delete a comment", description = "Delete a comment. Users can only delete their own comments. Admins can delete any comment.")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/api/comments/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") String id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "List comments for a specific post with pagination", description = "Get a paginated list of comments for a post. Public endpoint.")
    @SecurityRequirements() // No security required
    @GetMapping("/api/posts/{postId}/comments")
    public ResponseEntity<PagedResponse<CommentResponseDto>> listCommentsByPost(
            @PathVariable("postId") String postId,
            @Parameter(description = "Zero-based page index", in = ParameterIn.QUERY, schema = @Schema(defaultValue = "0"))
            @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of records per page", in = ParameterIn.QUERY, schema = @Schema(defaultValue = "10"))
            @RequestParam(value = "size", defaultValue = "10") int size) {
        PagedResponse<CommentResponseDto> response = commentService.listCommentsByPostId(postId, page, size);
        return ResponseEntity.ok(response);
    }
}