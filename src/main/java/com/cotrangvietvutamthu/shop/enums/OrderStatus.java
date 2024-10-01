package com.dirty.shop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    ORDER(0, false),
    CANCELLED(1, true),
    REFUSED(1, true),
    ACCEPTED(1, false),
    DELIVERY(2, false),
    DONE(3, true),
    ;

    private final int level;
    private final boolean finalStatus;
}
