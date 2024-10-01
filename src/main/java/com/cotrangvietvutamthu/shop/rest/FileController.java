package com.dirty.shop.rest;

import com.dirty.shop.base.Response;
import com.dirty.shop.base.WebConstants;
import com.dirty.shop.dto.response.FileUploadResponse;

import com.dirty.shop.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(WebConstants.API_FILE_PREFIX_V1)
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<Response<FileUploadResponse>> upload(@RequestParam MultipartFile file) {
        return ResponseEntity.ok(Response.success(fileService.uploadFile(file)));
    }

}
