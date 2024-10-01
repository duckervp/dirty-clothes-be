package com.dirty.shop.service.impl;

import com.dirty.shop.base.WebConstants;
import com.dirty.shop.config.property.FileProperty;
import com.dirty.shop.dto.response.FileUploadResponse;
import com.dirty.shop.enums.apicode.FileApiCode;
import com.dirty.shop.exception.ApiException;
import com.dirty.shop.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileProperty fileProperty;

    private final String[] allowedExtensions = {"png", "jpg", "gif", "webp"};


    @Override
    public void deleteFile(String url) {
        if (!StringUtils.hasText(url)) {
            return;
        }
        String fileName = getFilenameFromUrl(url);
        delete(fileName);
    }

    @Override
    public FileUploadResponse uploadFile(MultipartFile file) {
        byte[] fileBytes;
        try {
            fileBytes = file.getBytes();
        } catch (IOException e) {
            throw new ApiException(FileApiCode.INVALID_MULTIPART_FILE);
        }

        String extension = isValidTypeFile(file);
        if (Objects.isNull(extension)) {
            throw new ApiException(FileApiCode.FILE_TYPE_NOT_ALLOWED);
        }

        if (!extension.equals("webp") && !extension.equalsIgnoreCase("gif")) {
            extension = "webp";
        }

        byte[] rankIconToSave = extension.equals("webp") || extension.equalsIgnoreCase("gif") ? fileBytes
                : convertToWebP(fileBytes);
        return FileUploadResponse.builder()
                .url(produceImageUrl(saveFile(rankIconToSave, extension)))
                .build();
    }

    private void delete(String filename) {
        Path path = Paths.get(getRootPath(), filename);
        try {
            log.debug("Delete file {} result: {}", filename, Files.deleteIfExists(path));
        } catch (IOException e) {
            throw new ApiException(FileApiCode.IO_EXCEPTION);
        }
    }

    private String saveFile(byte[] file, String extension) {
        String fileName = UUID.randomUUID() + "." + extension;
        Path folderPath = Paths.get(getRootPath());
        if (!Files.exists(folderPath)) {
            try {
                Files.createDirectories(folderPath);
            } catch (IOException e) {
                log.error("Error: ", e);
                throw new ApiException(FileApiCode.IO_EXCEPTION);
            }
        }
        Path destination = Paths.get(folderPath.toString(), fileName);
        try {
            Files.write(destination, file);
        } catch (IOException e) {
            log.error("Error: ", e);
            throw new ApiException(FileApiCode.IO_EXCEPTION);
        }
        return fileName;
    }

    private String isValidTypeFile(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null) {
            throw new IllegalArgumentException("Original filename is not available.");
        }

        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
        for (String allowed : allowedExtensions) {
            if (allowed.equalsIgnoreCase(fileExtension)) return fileExtension;
        }
        return null;
    }

    private byte[] convertToWebP(byte[] input) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(input);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BufferedImage image = ImageIO.read(inputStream);
            ImageIO.write(image, "webp", outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            log.error("Error: ", e);
            throw new ApiException(FileApiCode.IO_EXCEPTION);
        }
    }

    private String produceImageUrl(String filename) {
        return String.format("%s%s/%s",
                fileProperty.getDomain(),
                WebConstants.UPLOADED_FILE_PREFIX,
                filename
        );
    }

    private String getRootPath() {
        return fileProperty.getRootPath();
    }

    private String getFilenameFromUrl(String url) {
        String[] urlParts = url.split("/");
        String filename = urlParts[urlParts.length - 1];
        return filename.strip();
    }
}
