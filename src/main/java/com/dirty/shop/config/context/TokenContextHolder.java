package com.dirty.shop.config.context;

import com.dirty.shop.dto.Token;
import com.dirty.shop.utils.SerializeUtils;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
@Slf4j
public class TokenContextHolder {
    private static final Map<Long, String> ACCESS_TOKEN_CONTEXT = new ConcurrentHashMap<>();
    private static final Map<Long, String> REFRESH_TOKEN_CONTEXT = new ConcurrentHashMap<>();

    private static void setToken(Long userId, Token token, Map<Long, String> context) {
        context.put(userId, SerializeUtils.toJson(token));
    }

    public static Token getToken(Long userId, Map<Long, String> context) {
        if (context.containsKey(userId)) {
            Token token = SerializeUtils.fromJson(context.get(userId), Token.class);
            if (token.getExpiredAt().isAfter(Instant.now())) {
                context.remove(userId);
                return null;
            }
            return token;
        }
        return null;
    }

    public static void setUserRefreshToken(Long userId, Token token) {
        setToken(userId, token, REFRESH_TOKEN_CONTEXT);
    }

    public static void setUserAccessToken(Long userId, Token token) {
        setToken(userId, token, ACCESS_TOKEN_CONTEXT);
    }

    public static Token getUserRefreshToken(Long userId) {
        return getToken(userId, REFRESH_TOKEN_CONTEXT);
    }

    public static Token getUserAccessToken(Long userId) {
        return getToken(userId, ACCESS_TOKEN_CONTEXT);
    }

}
