package com.backery.backery_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * Security Configuration for Bakery Management Application
 * 
 * This class configures security settings including:
 * - Password encoding using BCrypt
 * - CSRF protection
 * - Security headers
 * - Session management
 */
@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    /**
     * BCryptPasswordEncoder Bean
     * Uses strength 12 for good balance between security and performance
     * 
     * @return PasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    /**
     * Add security interceptor for CSRF token validation
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityHeadersInterceptor());
    }
}
