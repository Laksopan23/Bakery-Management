package com.backery.backery_management.util;

import org.apache.commons.lang3.StringEscapeUtils;
import java.util.regex.Pattern;

/**
 * Input Sanitization Utility
 * 
 * Provides methods to sanitize and validate user inputs to prevent:
 * - Cross-Site Scripting (XSS) attacks
 * - SQL Injection (for future database implementation)
 * - Path traversal attacks
 * - Other common injection vulnerabilities
 */
public class InputSanitizer {

    // Pattern to detect potentially dangerous characters for file paths
    private static final Pattern DANGEROUS_PATH_CHARS = Pattern.compile("[<>:\"|?*\\\\]");
    
    // Pattern to detect SQL injection attempts
    private static final Pattern SQL_INJECTION_PATTERN = Pattern.compile(
        "(--|;|'|\"|\\/\\*|\\*\\/|xp_|sp_|exec|execute|select|insert|update|delete|drop|create|alter|union|declare)",
        Pattern.CASE_INSENSITIVE);

    /**
     * Sanitize HTML content to prevent XSS attacks
     * @param input Unsanitized user input
     * @return HTML-escaped string safe for display
     */
    public static String sanitizeHtml(String input) {
        if (input == null) {
            return null;
        }
        return StringEscapeUtils.escapeHtml4(input.trim());
    }

    /**
     * Sanitize file paths to prevent directory traversal attacks
     * @param filename User-provided filename
     * @return Sanitized filename safe for file operations
     */
    public static String sanitizeFileName(String filename) {
        if (filename == null) {
            return null;
        }
        
        // Remove path traversal attempts
        filename = filename.replaceAll("\\.\\.[\\\\/]", "");
        filename = filename.replaceAll("^[\\\\/]", "");
        
        // Remove dangerous characters
        filename = DANGEROUS_PATH_CHARS.matcher(filename).replaceAll("_");
        
        return filename;
    }

    /**
     * Detect potential SQL injection attempts
     * @param input User input to check
     * @return true if potential SQL injection is detected
     */
    public static boolean detectSqlInjection(String input) {
        if (input == null) {
            return false;
        }
        return SQL_INJECTION_PATTERN.matcher(input).find();
    }

    /**
     * Validate email format with length check
     * @param email Email address to validate
     * @return true if email is valid
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        
        // Length check to prevent DoS
        if (email.length() > 254) {
            return false;
        }
        
        // Basic email validation
        Pattern emailPattern = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        return emailPattern.matcher(email).matches();
    }

    /**
     * Validate username format
     * @param username Username to validate
     * @return true if username is valid
     */
    public static boolean isValidUsername(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }
        
        // Length checks
        if (username.length() < 3 || username.length() > 20) {
            return false;
        }
        
        // Pattern: alphanumeric, underscore, hyphen only
        Pattern usernamePattern = Pattern.compile("^[A-Za-z0-9_-]{3,20}$");
        return usernamePattern.matcher(username).matches();
    }

    /**
     * Validate password strength
     * Requirements:
     * - Minimum 8 characters
     * - At least one uppercase letter
     * - At least one lowercase letter
     * - At least one digit
     * - At least one special character (@$!%*?&)
     * 
     * @param password Password to validate
     * @return true if password meets strength requirements
     */
    public static boolean isStrongPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        
        // Length check to prevent DoS
        if (password.length() > 128) {
            return false;
        }
        
        // Password strength pattern
        Pattern passwordPattern = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
        return passwordPattern.matcher(password).matches();
    }

    /**
     * Validate file extension for allowed types
     * @param filename Filename to check
     * @param allowedExtensions Array of allowed extensions (e.g., {"jpg", "png", "gif"})
     * @return true if file extension is allowed
     */
    public static boolean isAllowedFileType(String filename, String[] allowedExtensions) {
        if (filename == null || allowedExtensions == null) {
            return false;
        }
        
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        
        for (String allowed : allowedExtensions) {
            if (extension.equals(allowed.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validate file size to prevent DoS
     * @param fileSize File size in bytes
     * @param maxSizeInMB Maximum allowed size in megabytes
     * @return true if file size is within limits
     */
    public static boolean isValidFileSize(long fileSize, int maxSizeInMB) {
        long maxSizeInBytes = (long) maxSizeInMB * 1024 * 1024;
        return fileSize <= maxSizeInBytes && fileSize > 0;
    }

    /**
     * Escape user input for safe database queries (parameterized queries preferred)
     * @param input User input to escape
     * @return Escaped string
     */
    public static String escapeSqlString(String input) {
        if (input == null) {
            return null;
        }
        return input.replace("'", "''").replace("\"", "\"\"");
    }
}
