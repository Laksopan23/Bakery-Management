package com.backery.backery_management.service;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.backery.backery_management.dao.UserDAO;
import com.backery.backery_management.model.User;

@Service
public class UserService {

    private final UserDAO userDAO = new UserDAO();
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    
    // Enhanced email validation pattern - RFC 5322 simplified
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    // Username: alphanumeric, underscore, hyphen, 3-20 chars
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z0-9_-]{3,20}$");
    
    // Password: minimum 8 chars, at least one uppercase, one lowercase, one digit
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public boolean registerUser(String username, String password, String email, String role) {
        if (!isValidRegistration(username, password, email)) {
            System.out.println("Registration failed: Invalid data - Username: " + username + ", Email: " + email);
            return false;
        }
        if (userDAO.isUsernameExists(username)) {
            System.out.println("Registration failed: Username already exists - " + username);
            return false;
        }
        List<User> users = userDAO.getAllUsers();
        int newId = users.isEmpty() ? 1 : users.get(users.size() - 1).getId() + 1;
        
        // Hash password before storing
        String hashedPassword = passwordEncoder.encode(password);
        User newUser = new User(newId, username, hashedPassword, email, role != null ? role : "User");
        return userDAO.saveUser(newUser);
    }

    public boolean authenticateUser(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            return false;
        }
        User user = userDAO.findByUsername(username);
        if (user == null) {
            return false;
        }
        // Use BCrypt to compare passwords
        return passwordEncoder.matches(password, user.getPassword());
    }

    private boolean isValidRegistration(String username, String password, String email) {
        // Null and empty checks
        if (username == null || username.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || password == null || password.isEmpty()) {
            return false;
        }
        
        // Length checks to prevent DoS
        if (username.length() > 100 || email.length() > 254 || password.length() > 128) {
            return false;
        }
        
        // Pattern validation
        return USERNAME_PATTERN.matcher(username.trim()).matches()
                && PASSWORD_PATTERN.matcher(password).matches()
                && EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public boolean updateUser(User user) {
        if (!isValidUser(user)) {
            System.out.println("Update failed: Invalid user data - " + user);
            return false;
        }
        User existingUser = getUserById(user.getId());
        if (existingUser == null) {
            System.out.println("Update failed: User not found - ID: " + user.getId());
            return false;
        }
        // Check if the new username is taken by another user
        User userWithNewUsername = userDAO.findByUsername(user.getUsername());
        if (userWithNewUsername != null && userWithNewUsername.getId() != user.getId()) {
            System.out.println("Update failed: Username already taken - " + user.getUsername());
            return false;
        }
        userDAO.updateUser(user);
        System.out.println("User updated successfully: " + user);
        return true;
    }

    public boolean deleteUser(int id) {
        User user = getUserById(id);
        if (user != null) {
            userDAO.deleteUser(id);
            return true;
        }
        return false;
    }

    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    public User getUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    private boolean isValidUser(User user) {
        if (user == null || user.getUsername() == null || user.getUsername().trim().isEmpty()
                || user.getEmail() == null || user.getEmail().trim().isEmpty()
                || user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return false;
        }
        if (user.getRole() == null || (!user.getRole().equals("Admin") && !user.getRole().equals("User"))) {
            return false;
        }
        return USERNAME_PATTERN.matcher(user.getUsername().trim()).matches()
                && PASSWORD_PATTERN.matcher(user.getPassword().trim()).matches()
                && EMAIL_PATTERN.matcher(user.getEmail().trim()).matches();
    }

    private boolean userExists(int id) {
        return getUserById(id) != null;
    }
}
