package com.dirty.shop.base;

import com.dirty.shop.enums.apicode.CommonApiCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Response<T> {
    private int code;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> Response<T> with(ApiCode apiCode, T data) {
        return Response
                .<T>builder()
                .code(apiCode.getCode())
                .message(apiCode.getMessage())
                .data(data)
                .build();
    }

    public static <T> Response<T> with(ApiCode apiCode) {
        return Response
                .<T>builder()
                .code(apiCode.getCode())
                .message(apiCode.getMessage())
                .data(null)
                .build();
    }

    public static <T> Response<T> success(T data) {
        return Response.with(CommonApiCode.SUCCESS, data);
    }

    public static <T> Response<T> error(String msg) {
        return Response
                .<T>builder()
                .code(CommonApiCode.ERROR.getCode())
                .message(msg)
                .build();
    }
}
