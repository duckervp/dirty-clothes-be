package com.dirty.shop.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class Token {
    private String value;
    private Instant expiredAt;

    public static Token from(String value, Instant expiredAt) {
        return Token.builder().value(value).expiredAt(expiredAt).build();
    }
}
