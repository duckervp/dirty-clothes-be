package com.dirty.shop.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private Instant refreshTokenExpiredAt;
    private String name;
    private String email;
}
