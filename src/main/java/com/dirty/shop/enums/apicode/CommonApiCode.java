package com.dirty.shop.enums.apicode;

import com.dirty.shop.base.ApiCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CommonApiCode implements ApiCode {

    SUCCESS(200, "Success"),
    ERROR(500, "Error"),
    INVALID_PAYLOAD(400, "Invalid Payload"),
    NO_MERCHANT_AVAILABLE(402, "No merchant available"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not found record"),
    INVALID_QUERY(405, "Invalid sql query"),
    REQUIRED_BRAND_ID(406, "Required brand info"),
    DUPLICATED_RECORD(407, "Duplicated record"),
    CANNOT_ACQUIRE_LOCK(408, "Cannot acquire lock"),
    BRAND_ID_NOT_FOUND(409, "Brand Id not found")
    ;

    private final Integer code;
    private final String message;
}
