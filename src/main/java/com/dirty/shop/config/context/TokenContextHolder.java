package com.dirty.shop.config.context;

import com.dirty.shop.dto.Token;
import com.dirty.shop.enums.apicode.AuthApiCode;
import com.dirty.shop.exception.ApiException;
import com.dirty.shop.utils.SerializeUtils;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
@Slf4j
public class TokenContextHolder {
    private static final Map<String, String> ACCESS_TOKEN_CONTEXT = new ConcurrentHashMap<>();
    private static final Map<String, String> REFRESH_TOKEN_CONTEXT = new ConcurrentHashMap<>();

    private static void setToken(String key, Token token, Map<String, String> context) {
        context.put(key, SerializeUtils.toJson(token));
    }

    public static Token getToken(String key, Map<String, String> context) {
        return getToken(key, context, null, null);
    }

    public static void setUserRefreshToken(Token token) {
        setToken(token.getValue(), token, REFRESH_TOKEN_CONTEXT);
    }

    public static void setUserAccessToken(Token token) {
        setToken(token.getUserId().toString(), token, ACCESS_TOKEN_CONTEXT);
    }

    public static Token getUserRefreshToken(String refreshToken) {
        return getToken(refreshToken, REFRESH_TOKEN_CONTEXT);
    }

    public static Token getUserRefreshToken(String refreshToken,
                                            AuthApiCode invalidTokenCode,
                                            AuthApiCode tokenExpireCode) {
        return getToken(refreshToken, REFRESH_TOKEN_CONTEXT, invalidTokenCode, tokenExpireCode);
    }

    public static Token getUserAccessToken(Long userId) {
        return getToken(userId.toString(), ACCESS_TOKEN_CONTEXT);
    }

    public static Token getToken(String key, Map<String, String> context,
                                 AuthApiCode invalidTokenCode,
                                 AuthApiCode tokenExpireCode) {
        if (context.containsKey(key)) {
            Token token = SerializeUtils.fromJson(context.get(key), Token.class);
            if (token.getExpiredAt().isBefore(Instant.now())) {
                context.remove(key);
                if (Objects.nonNull(tokenExpireCode)) {
                    throw new ApiException(tokenExpireCode);
                }
                return null;
            }
            return token;
        }
        if (Objects.nonNull(invalidTokenCode)) {
            throw new ApiException(invalidTokenCode);
        }
        return null;
    }

    public static void removeToken(String key, Map<String, String> context) {
        context.remove(key);
    }

    public static void removeAccessToken(String key, Map<String, String> context) {
        removeToken(key, ACCESS_TOKEN_CONTEXT);
    }

    public static void removeRefreshToken(String key) {
        removeToken(key, REFRESH_TOKEN_CONTEXT);
    }

}
