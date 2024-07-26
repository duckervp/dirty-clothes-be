package com.dirty.shop.service;

import com.dirty.shop.dto.Token;
import com.dirty.shop.model.User;

import java.time.Instant;

public interface AuthTokenService {

    Token createRefreshToken(Long userId);

    long getExpireDate(Instant expiredAt);

    String createAccessToken(User user);

    String createAccessToken(User user, long expireMils);

}
