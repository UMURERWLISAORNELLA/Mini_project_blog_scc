package com.example.blogmanagement.service.impl;

import com.example.blogmanagement.dto.CommentRequestDto;
import com.example.blogmanagement.dto.CommentResponseDto;
import com.example.blogmanagement.dto.PagedResponse;
import com.example.blogmanagement.document.Comment;
import com.example.blogmanagement.entity.Role;
import com.example.blogmanagement.entity.User;
import com.example.blogmanagement.exception.ResourceNotFoundException;
import com.example.blogmanagement.exception.BadRequestException;
import com.example.blogmanagement.exception.UnauthorizedException;
import com.example.blogmanagement.repository.CommentRepository;
import com.example.blogmanagement.repository.PostRepository;
import com.example.blogmanagement.repository.UserRepository;
import com.example.blogmanagement.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Default implementation of {@link CommentService}. Manages CRUD operations
 * for comments, validating both the associated post and author exist.
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public CommentResponseDto createComment(CommentRequestDto request) {
        // Verify the referenced post exists
        postRepository.findById(request.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        // Verify the author exists
        userRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));

        Comment comment = Comment.builder()
                .postId(request.getPostId())
                .authorId(request.getAuthorId())
                .content(request.getContent())
                .build();
        Comment saved = commentRepository.save(comment);
        return mapToResponse(saved);
    }

    @Override
    public CommentResponseDto getComment(String commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        return mapToResponse(comment);
    }

    @Override
    public CommentResponseDto updateComment(String commentId, CommentRequestDto request) {
        Comment existing = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        
        // Check authorization - user must own the comment or be an admin
        User currentUser = getCurrentUser();
        if (!canModifyComment(currentUser, existing)) {
            throw new UnauthorizedException("You are not authorized to update this comment");
        }
        
        // In a real‑world API a comment's post and author cannot be changed. If
        // different identifiers are supplied, reject the request to prevent
        // accidental reassignment.
        if (!existing.getPostId().equals(request.getPostId())) {
            throw new BadRequestException("Post of a comment cannot be changed");
        }
        if (!existing.getAuthorId().equals(request.getAuthorId())) {
            throw new BadRequestException("Author of a comment cannot be changed");
        }
        existing.setContent(request.getContent());
        Comment updated = commentRepository.save(existing);
        return mapToResponse(updated);
    }

    @Override
    public void deleteComment(String commentId) {
        Comment existing = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        
        // Check authorization - user must own the comment or be an admin
        User currentUser = getCurrentUser();
        if (!canModifyComment(currentUser, existing)) {
            throw new UnauthorizedException("You are not authorized to delete this comment");
        }
        
        commentRepository.delete(existing);
    }

    @Override
    public PagedResponse<CommentResponseDto> listCommentsByPostId(String postId, int page, int size) {
        // Ensure the post exists before listing comments to return 404 if not found
        postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
        Page<Comment> commentPage = commentRepository.findByPostId(postId, pageable);
        List<CommentResponseDto> content = commentPage.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        boolean last = commentPage.getNumber() >= commentPage.getTotalPages() - 1;
        return new PagedResponse<>(
                content,
                commentPage.getNumber(),
                commentPage.getSize(),
                commentPage.getTotalElements(),
                commentPage.getTotalPages(),
                last);
    }

    /**
     * Map a Comment document to its response DTO representation.
     */
    private CommentResponseDto mapToResponse(Comment comment) {
        // Fetch the username of the author. The author existence has been validated
        // on creation/update. If the user cannot be found (edge case), fall back
        // to null to avoid throwing an exception during mapping.
        String authorUsername = userRepository.findById(comment.getAuthorId())
                .map(u -> u.getUsername())
                .orElse(null);
        return CommentResponseDto.builder()
                .id(comment.getId())
                .postId(comment.getPostId())
                .authorUsername(authorUsername)
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    /**
     * Get the currently authenticated user.
     *
     * @return the current user
     */
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("You must be logged in to perform this action");
        }
        return (User) authentication.getPrincipal();
    }

    /**
     * Check if the current user can modify a comment.
     * Users can modify their own comments. Admins can modify any comment.
     *
     * @param user the current user
     * @param comment the comment to check
     * @return true if the user can modify the comment, false otherwise
     */
    private boolean canModifyComment(User user, Comment comment) {
        // Admin can modify any comment
        if (user.getRoles().contains(Role.ADMIN)) {
            return true;
        }
        // User can modify their own comments
        return user.getId().equals(comment.getAuthorId());
    }
}