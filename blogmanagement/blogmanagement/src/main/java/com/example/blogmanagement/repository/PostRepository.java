package com.example.blogmanagement.repository;

import com.example.blogmanagement.document.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository for performing CRUD operations on Post documents stored in MongoDB.
 * Spring Data derives implementations of query methods from their names.
 */
public interface PostRepository extends MongoRepository<Post, String> {

    /**
     * Search for posts containing the given keyword in either the title or content.
     * Results are paginated via the provided Pageable.
     *
     * @param title keyword to search in titles
     * @param content keyword to search in content
     * @param pageable pagination information
     * @return a page of matching posts
     */
    Page<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content, Pageable pageable);

    /**
     * List posts authored by a specific user.
     *
     * @param authorId identifier of the author
     * @param pageable pagination information
     * @return a page of posts authored by the given user
     */
    Page<Post> findByAuthorId(Long authorId, Pageable pageable);
}