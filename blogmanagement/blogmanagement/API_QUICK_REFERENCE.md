# API Quick Reference - Authentication & Authorization

## 🔐 Authentication Endpoints

### Register New User
```http
POST /api/users/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "SecurePass123"
}
```

### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "john_doe",
  "password": "SecurePass123"
}

Response:
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "roles": ["ROLE_USER"]
}
```

## 📝 Blog Post Endpoints

### Create Post (Authenticated)
```http
POST /api/posts
Authorization: Bearer {token}
Content-Type: application/json

{
  "title": "My Blog Post",
  "content": "Post content here",
  "authorId": 1
}
```

### Get All Posts (Public)
```http
GET /api/posts?page=0&size=10&search=keyword
```

### Get Single Post (Public)
```http
GET /api/posts/{postId}
```

### Update Post (Owner or Admin)
```http
PUT /api/posts/{postId}
Authorization: Bearer {token}
Content-Type: application/json

{
  "title": "Updated Title",
  "content": "Updated content",
  "authorId": 1
}
```

### Delete Post (Owner or Admin)
```http
DELETE /api/posts/{postId}
Authorization: Bearer {token}
```

## 💬 Comment Endpoints

### Create Comment (Authenticated)
```http
POST /api/comments
Authorization: Bearer {token}
Content-Type: application/json

{
  "postId": "64abc123def456789",
  "content": "Great post!",
  "authorId": 1
}
```

### Get Comments for Post (Public)
```http
GET /api/posts/{postId}/comments?page=0&size=10
```

### Get Single Comment (Public)
```http
GET /api/comments/{commentId}
```

### Update Comment (Owner or Admin)
```http
PUT /api/comments/{commentId}
Authorization: Bearer {token}
Content-Type: application/json

{
  "postId": "64abc123def456789",
  "content": "Updated comment",
  "authorId": 1
}
```

### Delete Comment (Owner or Admin)
```http
DELETE /api/comments/{commentId}
Authorization: Bearer {token}
```

## 👥 User Management

### Get User (Public)
```http
GET /api/users/{userId}
```

### Delete User (Admin Only)
```http
DELETE /api/users/{userId}
Authorization: Bearer {admin-token}
```

## 🔑 Authorization Matrix

| Endpoint | Public | USER | ADMIN |
|----------|--------|------|-------|
| POST /api/auth/login | ✅ | ✅ | ✅ |
| POST /api/users/register | ✅ | ✅ | ✅ |
| GET /api/posts | ✅ | ✅ | ✅ |
| GET /api/posts/{id} | ✅ | ✅ | ✅ |
| POST /api/posts | ❌ | ✅ (own) | ✅ |
| PUT /api/posts/{id} | ❌ | ✅ (own) | ✅ (any) |
| DELETE /api/posts/{id} | ❌ | ✅ (own) | ✅ (any) |
| GET /api/comments | ✅ | ✅ | ✅ |
| GET /api/comments/{id} | ✅ | ✅ | ✅ |
| POST /api/comments | ❌ | ✅ (own) | ✅ |
| PUT /api/comments/{id} | ❌ | ✅ (own) | ✅ (any) |
| DELETE /api/comments/{id} | ❌ | ✅ (own) | ✅ (any) |
| GET /api/users/{id} | ✅ | ✅ | ✅ |
| DELETE /api/users/{id} | ❌ | ❌ | ✅ |

## 🚫 Common Error Responses

### 401 Unauthorized (Missing/Invalid Token)
```json
{
  "timestamp": "2025-11-09T10:30:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid username or password",
  "path": "/api/auth/login"
}
```

### 403 Forbidden (Insufficient Permissions)
```json
{
  "timestamp": "2025-11-09T10:30:00",
  "status": 403,
  "error": "Forbidden",
  "message": "You are not authorized to update this post",
  "path": "/api/posts/64abc123def456789"
}
```

### 404 Not Found
```json
{
  "timestamp": "2025-11-09T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Post not found",
  "path": "/api/posts/invalid-id"
}
```

## 🧪 Testing with cURL

### Register → Login → Create Post Flow
```bash
# 1. Register
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","password":"pass123"}'

# 2. Login (save the token)
TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"pass123"}' \
  | jq -r '.accessToken')

# 3. Create a post
curl -X POST http://localhost:8080/api/posts \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"title":"Test Post","content":"Content here","authorId":1}'
```

## 🌐 Swagger UI

Access interactive API documentation at:
```
http://localhost:8080/swagger-ui.html
```

1. Click "Authorize" button
2. Enter: `Bearer {your-token}`
3. Test all endpoints directly

## 💡 Tips

1. **Token Format**: Always include "Bearer " prefix in Authorization header
2. **Author ID**: Must match the authenticated user's ID (unless admin)
3. **Token Expiry**: Tokens expire after 24 hours
4. **Role Assignment**: New users get USER role automatically
5. **Admin Access**: Requires manual database update to assign ADMIN role

## 🔒 Security Best Practices

- ✅ Never commit JWT secrets to version control
- ✅ Use HTTPS in production
- ✅ Store tokens securely (not in localStorage for sensitive apps)
- ✅ Implement token refresh mechanism for production
- ✅ Use environment variables for secrets
- ✅ Regularly rotate JWT secrets
- ✅ Implement rate limiting for authentication endpoints
