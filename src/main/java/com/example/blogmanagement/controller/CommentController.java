package com.example.blogmanagement.controller;

import com.example.blogmanagement.dto.CommentRequestDto;
import com.example.blogmanagement.dto.CommentResponseDto;
import com.example.blogmanagement.dto.PagedResponse;
import com.example.blogmanagement.service.CommentService;
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
 * REST controller for managing comments on blog posts. Supports standard CRUD
 * operations as well as listing comments for a specific post with pagination.
 */
@RestController
@Tag(name = "Comments", description = "Operations on comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Create a new comment")
    @PostMapping("/api/comments")
    public ResponseEntity<CommentResponseDto> createComment(@Valid @RequestBody CommentRequestDto request) {
        CommentResponseDto response = commentService.createComment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get a comment by ID")
    @GetMapping("/api/comments/{id}")
    public ResponseEntity<CommentResponseDto> getComment(@PathVariable("id") String id) {
        return ResponseEntity.ok(commentService.getComment(id));
    }

    @Operation(summary = "Update a comment")
    @PutMapping("/api/comments/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable("id") String id,
                                                           @Valid @RequestBody CommentRequestDto request) {
        return ResponseEntity.ok(commentService.updateComment(id, request));
    }

    @Operation(summary = "Delete a comment")
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") String id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "List comments for a specific post with pagination")
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