package com.dirty.shop.rest;

import com.dirty.shop.base.Response;
import com.dirty.shop.base.WebConstants;
import com.dirty.shop.dto.request.ChangePasswordRequest;
import com.dirty.shop.dto.request.LoginRequest;
import com.dirty.shop.dto.request.RefreshTokenRequest;
import com.dirty.shop.dto.request.RegisterRequest;
import com.dirty.shop.dto.response.LoginResponse;
import com.dirty.shop.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/refresh-token")
    public ResponseEntity<Response<String>> removeRefreshToken(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(Response.success(authService.removeRefreshToken(request)));
    }

    @PatchMapping("/change-password")
    public ResponseEntity<Response<String>> changePassword(@RequestBody ChangePasswordRequest request) {
        return ResponseEntity.ok(Response.success(authService.changePassword(request)));
    }

}
