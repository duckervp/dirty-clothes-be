package com.dirty.shop.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileUploadResponse {
    private String url;
}
