package com.dirty.shop.service.impl;

import com.dirty.shop.config.context.TokenContextHolder;
import com.dirty.shop.config.property.AuthProperty;
import com.dirty.shop.dto.Token;
import com.dirty.shop.model.User;
import com.dirty.shop.service.AuthTokenService;
import com.dirty.shop.utils.AuthTokenUtils;
import com.dirty.shop.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthTokenServiceImpl implements AuthTokenService {

    private final AuthProperty authProperty;

    @Override
    public Token createRefreshToken(Long userId) {
        Instant expiredAt = DateTimeUtils.getCurrentInstantMilliseconds().plusMillis(
            authProperty.getRefreshTokenExpirationMils());
        String value = UUID.randomUUID().toString();

        Token token = Token.from(value, expiredAt);

        TokenContextHolder.setUserRefreshToken(userId, token);

        return token;
    }

    @Override
    public long getExpireDate(Instant expiredAt) {
        Instant maxExpiryDate = DateTimeUtils.plusMils(DateTimeUtils.now(), authProperty.getAccessTokenExpirationMils());
        if (expiredAt.isBefore(maxExpiryDate)) {
            return DateTimeUtils.milsBetween(DateTimeUtils.now(), expiredAt);
        } else {
            return authProperty.getAccessTokenExpirationMils();
        }
    }

    @Override
    public String createAccessToken(User user) {
        String value = AuthTokenUtils.createAccessToken(
            user.getId(),
            user.getUserInfo(),
            authProperty.getAccessTokenExpirationMils(),
            authProperty.getTokenSecret());

        Instant expiredAt = DateTimeUtils.getCurrentInstantMilliseconds().plusMillis(
            authProperty.getAccessTokenExpirationMils());

        Token token = Token.from(value, expiredAt);

        TokenContextHolder.setUserAccessToken(user.getId(), token);

        return value;
    }

    @Override
    public String createAccessToken(User user, long expireMils) {
        String value = AuthTokenUtils.createAccessToken(
            user.getId(),
            user.getUserInfo(),
            expireMils,
            authProperty.getTokenSecret());

        Instant expiredAt = DateTimeUtils.getCurrentInstantMilliseconds().plusMillis(
            expireMils);

        Token token = Token.from(value, expiredAt);

        TokenContextHolder.setUserAccessToken(user.getId(), token);

        return value;
    }

}
