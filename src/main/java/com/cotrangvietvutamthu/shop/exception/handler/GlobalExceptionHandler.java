package com.dirty.shop.exception.handler;

import com.dirty.shop.base.Response;
import com.dirty.shop.enums.apicode.AuthApiCode;
import com.dirty.shop.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
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

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Response<Void>> handleAccessDeniedException(AccessDeniedException e) {
        log.error("Error: ", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Response.with(AuthApiCode.PERMISSION_DENIED));
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<Response<Void>> handleInsufficientAuthenticationException(InsufficientAuthenticationException e) {
        log.error("Error: ", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Response.with(AuthApiCode.NEED_AUTHENTICATION));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Response<Void>> handleBadCredentialsException(BadCredentialsException e) {
        log.error("Error: ", e);
        return ResponseEntity.badRequest().body(Response.with(AuthApiCode.USERNAME_OR_PASSWORD_IS_INCORRECT));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Void>> handleException(Exception e) {
        log.error("Error: ", e);
        return ResponseEntity.badRequest().body(Response.error(e.getMessage()));
    }
}
