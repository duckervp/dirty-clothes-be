package com.dirty.shop.service;

import com.dirty.shop.dto.response.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileUploadResponse uploadFile(MultipartFile file);

    void deleteFile(String url);
}
