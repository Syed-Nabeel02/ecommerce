package com.ecommerce.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for CORS (Cross-Origin Resource Sharing).
 * Allows the frontend app to make API calls to the backend from a different domain.
 * Without this, browser security would block frontend-backend communication.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures which frontend URLs can access this backend.
     * Allows the Netlify-hosted frontend to communicate with the backend.
     * Permits all HTTP methods (GET, POST, PUT, etc.) and headers.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://mellow-unicorn-811456.netlify.app")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
