package com.dirty.shop.exception.handler;

import com.dirty.shop.base.Response;
import com.dirty.shop.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Response<Void>> handleApiException(ApiException e) {
        log.error("Error: ", e);
        return ResponseEntity.badRequest().body(Response.with(e.getErrorCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Void>> handleException(Exception e) {
        log.error("Error: ", e);
        return ResponseEntity.badRequest().body(Response.error(e.getMessage()));
    }
}
