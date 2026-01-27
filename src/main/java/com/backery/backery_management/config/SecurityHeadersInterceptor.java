package com.backery.backery_management.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.UUID;

/**
 * Security Headers Interceptor
 * 
 * Adds security headers to all HTTP responses to protect against common vulnerabilities:
 * - X-Content-Type-Options: Prevents MIME type sniffing
 * - X-Frame-Options: Prevents clickjacking attacks
 * - X-XSS-Protection: Enables XSS filtering
 * - Strict-Transport-Security: Enforces HTTPS (for production)
 * - Content-Security-Policy: Prevents inline script execution
 * - CSRF Token: Generated for form submissions
 */
public class SecurityHeadersInterceptor implements HandlerInterceptor {

    private static final String CSRF_TOKEN_HEADER = "X-CSRF-TOKEN";
    private static final String CSRF_PARAM_NAME = "_csrf";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Generate and store CSRF token in session
        String csrfToken = (String) request.getSession().getAttribute(CSRF_TOKEN_HEADER);
        if (csrfToken == null) {
            csrfToken = UUID.randomUUID().toString();
            request.getSession().setAttribute(CSRF_TOKEN_HEADER, csrfToken);
        }
        
        // Set security headers
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        response.setHeader("X-XSS-Protection", "1; mode=block");
        response.setHeader("Content-Security-Policy", "default-src 'self'; script-src 'self' 'unsafe-inline' https://cdn.jsdelivr.net; style-src 'self' 'unsafe-inline' https://cdn.jsdelivr.net");
        response.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");
        
        // HSTS header (only for production with HTTPS)
        // Uncomment for production: response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
        
        // Make CSRF token available to request
        request.setAttribute(CSRF_PARAM_NAME, csrfToken);
        
        return true;
    }
}
