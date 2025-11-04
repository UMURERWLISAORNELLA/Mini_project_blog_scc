package com.example.blogmanagement.repository;

import com.example.blogmanagement.document.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository for performing CRUD operations on Comment documents stored in MongoDB.
 */
public interface CommentRepository extends MongoRepository<Comment, String> {

    /**
     * Find all comments belonging to a specific post.
     *
     * @param postId identifier of the post
     * @param pageable pagination information
     * @return a page of comments belonging to the post
     */
    Page<Comment> findByPostId(String postId, Pageable pageable);

    /**
     * Delete all comments associated with a post. This method is used when
     * cascading a delete operation from a post to its comments.
     *
     * @param postId identifier of the post whose comments should be deleted
     */
    void deleteByPostId(String postId);
}