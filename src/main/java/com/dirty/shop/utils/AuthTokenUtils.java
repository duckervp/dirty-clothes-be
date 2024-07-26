package com.dirty.shop.utils;

import com.dirty.shop.enums.apicode.AuthApiCode;
import com.dirty.shop.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@UtilityClass
@Slf4j
public class AuthTokenUtils {
    final String error = "Error: ";

    public static String createAccessToken(Long memberId, User user, long accessTokenExpireMils,
                                           String tokenSecret) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpireMils);
        return Jwts.builder()
                .setSubject(Long.toString(memberId))
                .setClaims(Map.of("user", SerializeUtils.toJson(user.getUserInfo())))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(getSigningKey(tokenSecret), SignatureAlgorithm.HS256)
                .compact();
    }

    private static Key getSigningKey(String tokenSecret) {
        byte[] keyBytes = tokenSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static User loadUserFromJwt(String jwt, String tokenSecret) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey(tokenSecret)).build().parseClaimsJws(jwt)
                .getBody();
            return SerializeUtils.fromJson((String) claims.get("user"), User.class);
        } catch (ExpiredJwtException e) {
            Claims claims = e.getClaims();
            return SerializeUtils.fromJson((String) claims.get("user"), User.class);
        } catch (Exception e) {
            log.error(error, e);
            return null;
        }
    }

    public static Optional<AuthApiCode> validateToken(String authToken, String tokenSecret) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey(tokenSecret)).build().parseClaimsJws(authToken);
        } catch (SignatureException ex) {
            log.error(error, ex);
            return Optional.of(AuthApiCode.INVALID_JWT_SIGNATURE);
        } catch (MalformedJwtException ex) {
            log.error(error, ex);
            return Optional.of(AuthApiCode.INVALID_JWT_TOKEN);
        } catch (ExpiredJwtException ex) {
            log.error(error, ex);
            return Optional.of(AuthApiCode.JWT_TOKEN_EXPIRED);
        } catch (UnsupportedJwtException ex) {
            log.error(error, ex);
            return Optional.of(AuthApiCode.UNSUPPORTED_JWT_TOKEN);
        } catch (IllegalArgumentException ex) {
            log.error(error, ex);
            return Optional.of(AuthApiCode.JWT_CLAIMS_EMPTY);
        }
        return Optional.empty();
    }

}
