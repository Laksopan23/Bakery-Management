<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Login - Bakery Management</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f5f5f5;
        }
        .login-container {
            background-color: white;
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 400px;
        }
        .security-info {
            font-size: 0.85rem;
            color: #666;
            margin-top: 1rem;
            padding-top: 1rem;
            border-top: 1px solid #eee;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h2 class="text-center mb-4">🍰 Bakery Management Login</h2>
        
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <strong>Error:</strong> <c:out value="${error}" />
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        
        <c:if test="${not empty success}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <c:out value="${success}" />
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/" method="post" autocomplete="off" onsubmit="return validateLoginForm()">
            <!-- CSRF Token -->
            <input type="hidden" name="_csrf" value="${_csrf}" />
            
            <input type="text" style="display:none;" name="hidden" autocomplete="off"/>
            <input type="password" style="display:none;" name="hiddenPassword" autocomplete="off"/>
            <div class="mb-3">
                <label for="username" class="form-label">Username:</label>
                <input type="text" id="username" name="username" class="form-control" autocomplete="off" required maxlength="20" placeholder="Enter your username">
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password:</label>
                <input type="password" id="password" name="password" class="form-control" autocomplete="new-password" required maxlength="128" placeholder="Enter your password">
            </div>
            <button type="submit" class="btn btn-success w-100">Login</button>
        </form>
        <div class="text-center mt-3">
            <p>Don't have an account? <a href="${pageContext.request.contextPath}/signup">Sign up here</a></p>
        </div>
        <div class="security-info">
            <small><strong>Demo Credentials:</strong><br>Username: <code>Admin</code> | Password: <code>admin@12345</code></small>
        </div>
    </div>

    <script>
        function validateLoginForm() {
            var username = document.getElementById("username").value.trim();
            var password = document.getElementById("password").value;
            
            if (username === "" || password === "") {
                alert("Please fill in all fields");
                return false;
            }
            
            if (username.length < 3) {
                alert("Username must be at least 3 characters");
                return false;
            }
            
            return true;
        }
    </script>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
