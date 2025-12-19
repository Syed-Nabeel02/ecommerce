package com.ecommerce.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for Spring MVC web settings.
 * Configures how static resources (like images) are served.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * Maps /images/** URLs to the local images folder.
     * Legacy support: Old images stored locally are still accessible.
     * New images are uploaded to Cloudinary instead.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Legacy: Serve local images for backwards compatibility
        // New images are uploaded to Cloudinary and served via CDN
        registry.addResourceHandler("/images/**").addResourceLocations("file:images/");
    }
}
