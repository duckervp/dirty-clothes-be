package com.dirty.shop.rest;

import com.dirty.shop.base.Response;
import com.dirty.shop.base.WebConstants;
import com.dirty.shop.dto.request.LoginRequest;
import com.dirty.shop.dto.request.RefreshTokenRequest;
import com.dirty.shop.dto.request.RegisterRequest;
import com.dirty.shop.dto.response.LoginResponse;
import com.dirty.shop.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(WebConstants.API_AUTH_PREFIX_V1)
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Response<LoginResponse>> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(Response.success(authService.login(request)));
    }

    @PostMapping("/register")
    public ResponseEntity<Response<LoginResponse>> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(Response.success(authService.register(request)));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Response<LoginResponse>> refreshToken(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(Response.success(authService.refreshToken(request)));
    }

}
