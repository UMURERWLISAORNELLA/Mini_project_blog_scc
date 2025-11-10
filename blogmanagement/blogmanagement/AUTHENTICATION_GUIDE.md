# Blog Management API - Authentication & Authorization Guide

## Overview

This Spring Boot application provides a comprehensive blog management system with JWT-based authentication and role-based access control (RBAC). The system supports user registration, authentication, and authorization for managing blog posts and comments.

## Features Implemented

### 1. Authentication
- **User Registration**: New users can register with username, email, and password
- **User Login**: Authenticate with username and password to receive a JWT token
- **JWT Token**: Secure token-based authentication with 24-hour expiration

### 2. Role-Based Access Control (RBAC)
The system implements two roles:

#### USER Role
- Create, view, update, and delete **their own** blog posts
- Create, view, update, and delete **their own** comments
- View all public posts and comments

#### ADMIN Role
- All USER permissions
- Delete **any** user
- Delete **any** post (regardless of ownership)
- Delete **any** comment (regardless of ownership)

### 3. Protected Endpoints

#### Public Endpoints (No Authentication Required)
- `POST /api/auth/login` - User login
- `POST /api/users/register` - User registration
- `GET /api/posts/**` - View all posts
- `GET /api/comments/**` - View all comments
- `GET /api/users/{id}` - View user profile

#### Protected Endpoints (Authentication Required)
- `POST /api/posts` - Create a post (USER/ADMIN)
- `PUT /api/posts/{id}` - Update own post (USER) or any post (ADMIN)
- `DELETE /api/posts/{id}` - Delete own post (USER) or any post (ADMIN)
- `POST /api/comments` - Create a comment (USER/ADMIN)
- `PUT /api/comments/{id}` - Update own comment (USER) or any comment (ADMIN)
- `DELETE /api/comments/{id}` - Delete own comment (USER) or any comment (ADMIN)

#### Admin-Only Endpoints
- `DELETE /api/users/{id}` - Delete any user (ADMIN only)

## API Usage Examples

### 1. Register a New User

```bash
POST /api/users/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "SecurePass123"
}
```

**Response:**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "createdAt": "2025-11-09T10:30:00"
}
```

### 2. Login

```bash
POST /api/auth/login
Content-Type: application/json

{
  "username": "john_doe",
  "password": "SecurePass123"
}
```

**Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "roles": ["ROLE_USER"]
}
```

### 3. Create a Blog Post (Authenticated)

```bash
POST /api/posts
Authorization: Bearer <your-jwt-token>
Content-Type: application/json

{
  "title": "My First Blog Post",
  "content": "This is the content of my blog post.",
  "authorId": 1
}
```

### 4. Update Own Post (Authenticated)

```bash
PUT /api/posts/{postId}
Authorization: Bearer <your-jwt-token>
Content-Type: application/json

{
  "title": "Updated Blog Post Title",
  "content": "Updated content.",
  "authorId": 1
}
```

**Note:** Users can only update their own posts unless they have ADMIN role.

### 5. Delete a Post (Owner or Admin)

```bash
DELETE /api/posts/{postId}
Authorization: Bearer <your-jwt-token>
```

- **USER role**: Can only delete their own posts
- **ADMIN role**: Can delete any post

### 6. Create a Comment (Authenticated)

```bash
POST /api/comments
Authorization: Bearer <your-jwt-token>
Content-Type: application/json

{
  "postId": "64abc123def456789",
  "content": "Great post!",
  "authorId": 1
}
```

### 7. Delete a User (Admin Only)

```bash
DELETE /api/users/{userId}
Authorization: Bearer <admin-jwt-token>
```

**Note:** This endpoint is restricted to users with ADMIN role only.

## Authorization Flow

### Ownership-Based Authorization

The system implements ownership checks at the service layer:

1. **Posts**: 
   - Users can only update/delete posts where `authorId` matches their user ID
   - Admins can update/delete any post

2. **Comments**:
   - Users can only update/delete comments where `authorId` matches their user ID
   - Admins can update/delete any comment

### Authorization Error Responses

**401 Unauthorized** - Missing or invalid JWT token:
```json
{
  "timestamp": "2025-11-09T10:30:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid username or password",
  "path": "/api/auth/login"
}
```

**403 Forbidden** - User doesn't have permission:
```json
{
  "timestamp": "2025-11-09T10:30:00",
  "status": 403,
  "error": "Forbidden",
  "message": "You are not authorized to update this post",
  "path": "/api/posts/64abc123def456789"
}
```

## Security Configuration

### JWT Token Settings
- **Secret Key**: Configured in `application.yml` (should be environment variable in production)
- **Expiration**: 24 hours (86400000 ms)
- **Algorithm**: HS256 (HMAC with SHA-256)

### Password Security
- Passwords are hashed using BCrypt
- Original passwords are never stored or logged

### Security Headers
- CSRF protection disabled (stateless JWT authentication)
- Stateless session management
- CORS can be configured as needed

## Database Schema Changes

### User Table
New columns added:
- `roles` - Collection table storing user roles (USER, ADMIN)

Example table structure:
```sql
-- users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- user_roles table
CREATE TABLE user_roles (
    user_id BIGINT REFERENCES users(id),
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, role)
);
```

## Testing with Swagger UI

The API includes Swagger UI for interactive testing:

1. Navigate to `http://localhost:8080/swagger-ui.html`
2. Click on "Authorize" button
3. Enter your JWT token in the format: `Bearer <your-token>`
4. Test authenticated endpoints directly from the UI

## Creating an Admin User

By default, all registered users get the USER role. To create an admin:

1. Register a user through the API
2. Manually update the database to add ADMIN role:

```sql
INSERT INTO user_roles (user_id, role) VALUES (1, 'ADMIN');
```

## Dependencies Added

```xml
<!-- Spring Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- JWT Dependencies -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.5</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.5</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.5</version>
    <scope>runtime</scope>
</dependency>
```

## Environment Variables (Production)

For production deployment, configure these as environment variables:

```bash
JWT_SECRET=your-secure-secret-key-here
JWT_EXPIRATION_MS=86400000
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/blogdb
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=your-secure-password
SPRING_DATA_MONGODB_URI=mongodb://localhost:27017/blogdb
```

## Key Classes Added

### Security Package
- `JwtTokenProvider` - Generate and validate JWT tokens
- `JwtAuthenticationFilter` - Filter to authenticate requests using JWT
- `CustomUserDetailsService` - Load user details for authentication
- `SecurityConfig` - Spring Security configuration

### DTOs
- `LoginRequest` - Login credentials
- `AuthResponse` - Authentication response with JWT token

### Entities
- `Role` - Enum defining USER and ADMIN roles

### Exception
- `UnauthorizedException` - Custom exception for authorization failures

## Summary

The blog management system now includes comprehensive authentication and authorization:

✅ JWT-based authentication  
✅ User registration and login  
✅ Role-based access control (USER and ADMIN)  
✅ Ownership-based authorization for posts and comments  
✅ Protected endpoints with @PreAuthorize annotations  
✅ Admin capabilities to delete any user, post, or comment  
✅ Secure password hashing with BCrypt  
✅ Comprehensive error handling  

Users can now safely create, manage, and share blog content with proper access controls in place!
