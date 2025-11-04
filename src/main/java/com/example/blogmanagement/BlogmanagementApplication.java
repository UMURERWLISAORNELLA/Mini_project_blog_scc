package com.example.blogmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Blog Management application.
 *
 * This application demonstrates how to configure and use multiple data sources
 * simultaneously in Spring Boot. PostgreSQL is used to persist relational user
 * data, while MongoDB stores posts and comments. The application exposes a
 * RESTful API for registration, post management and comment management.
 */
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class BlogmanagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogmanagementApplication.class, args);
    }
}