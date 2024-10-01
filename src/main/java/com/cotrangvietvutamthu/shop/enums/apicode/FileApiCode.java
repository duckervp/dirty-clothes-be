package com.dirty.shop.enums.apicode;

import com.dirty.shop.base.ApiCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum FileApiCode implements ApiCode{
    IO_EXCEPTION(1000, "File IO error"),
    FILE_TYPE_NOT_ALLOWED(1001, "File type not allowed"),
    INVALID_MULTIPART_FILE(1002, "Invalid multipart file");

    private final Integer code;
    private final String message;
}
