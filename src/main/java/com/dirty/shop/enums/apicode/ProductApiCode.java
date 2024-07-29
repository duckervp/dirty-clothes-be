package com.dirty.shop.enums.apicode;

import com.dirty.shop.base.ApiCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProductApiCode implements ApiCode{
    PRODUCT_NOT_FOUND(2000, "Product not found"),
    FILE_TYPE_NOT_ALLOWED(1001, "File type not allowed"),
    INVALID_MULTIPART_FILE(1002, "Invalid multipart file");

    private final Integer code;
    private final String message;
}
