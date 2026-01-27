package com.backery.backery_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.backery.backery_management.model.User;
import com.backery.backery_management.service.UserService;
import com.backery.backery_management.util.InputSanitizer;

import jakarta.servlet.http.HttpSession;

/**
 * Authentication Controller
 * 
 * Handles user login, registration, and logout operations
 * with input validation and sanitization for security
 */
@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String loginPage(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            User user = userService.getUserByUsername(username);
            if (user != null && "Admin".equals(user.getRole())) {
                return "redirect:/home";
            } else {
                return "redirect:/products/customer";
            }
        }
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            User user = userService.getUserByUsername(username);
            if (user != null && "Admin".equals(user.getRole())) {
                return "redirect:/home";
            } else {
                return "redirect:/products/customer";
            }
        }
        return "signup";
    }

    @PostMapping("/")
    public String processLogin(@RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        // Input validation
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Username and password are required");
            return "redirect:/";
        }
        
        // Sanitize inputs
        String sanitizedUsername = InputSanitizer.sanitizeHtml(username);
        
        // Detect SQL injection attempts
        if (InputSanitizer.detectSqlInjection(sanitizedUsername) || 
            InputSanitizer.detectSqlInjection(password)) {
            redirectAttributes.addFlashAttribute("error", "Invalid input detected. Please try again.");
            return "redirect:/";
        }
        
        if (userService.authenticateUser(sanitizedUsername, password)) {
            session.setAttribute("username", sanitizedUsername);
            session.setAttribute("customerName", sanitizedUsername);
            User user = userService.getUserByUsername(sanitizedUsername);
            if (user != null) {
                session.setAttribute("role", user.getRole());
                session.setMaxInactiveInterval(1800); // 30 minutes
                if ("Admin".equals(user.getRole())) {
                    return "redirect:/home";
                }
            }
            return "redirect:/products/customer";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/";
        }
    }

    @PostMapping("/signup")
    public String processSignup(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam String email,
            RedirectAttributes redirectAttributes) {
        
        // Input validation
        if (username == null || username.trim().isEmpty() || 
            password == null || password.isEmpty() || 
            email == null || email.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "All fields are required");
            return "redirect:/signup";
        }
        
        // Sanitize inputs
        String sanitizedUsername = InputSanitizer.sanitizeHtml(username);
        String sanitizedEmail = InputSanitizer.sanitizeHtml(email);
        
        // Detect injection attempts
        if (InputSanitizer.detectSqlInjection(sanitizedUsername) || 
            InputSanitizer.detectSqlInjection(password) ||
            InputSanitizer.detectSqlInjection(sanitizedEmail)) {
            redirectAttributes.addFlashAttribute("error", "Invalid input detected. Please try again.");
            return "redirect:/signup";
        }
        
        // Validate email format
        if (!InputSanitizer.isValidEmail(sanitizedEmail)) {
            redirectAttributes.addFlashAttribute("error", "Invalid email format");
            return "redirect:/signup";
        }
        
        // Validate username format
        if (!InputSanitizer.isValidUsername(sanitizedUsername)) {
            redirectAttributes.addFlashAttribute("error", "Username must be 3-20 characters (alphanumeric, underscore, hyphen only)");
            return "redirect:/signup";
        }
        
        // Validate password strength
        if (!InputSanitizer.isStrongPassword(password)) {
            redirectAttributes.addFlashAttribute("error", 
                "Password must be at least 8 characters with uppercase, lowercase, digit, and special character (@$!%*?&)");
            return "redirect:/signup";
        }
        
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match");
            return "redirect:/signup";
        }
        
        String role = "User";
        if (userService.registerUser(sanitizedUsername, password, sanitizedEmail, role)) {
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please login with your new credentials.");
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("error", "Registration failed. Username might already exist or invalid data provided.");
            return "redirect:/signup";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("message", "You have been logged out successfully.");
        return "redirect:/";
    }
}

