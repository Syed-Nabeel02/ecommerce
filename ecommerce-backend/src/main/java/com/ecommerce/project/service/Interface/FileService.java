package com.ecommerce.project.service.Interface;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Service interface for file operations
 * Handles file uploads (primarily product images)
 */
public interface FileService {
    // Upload an image file to specified path, returns filename
    String uploadImage(String path, MultipartFile file) throws IOException;
}
