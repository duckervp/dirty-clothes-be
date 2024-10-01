package com.dirty.shop.enums.apicode;

import com.dirty.shop.base.ApiCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderApiCode implements ApiCode{
    ORDER_NOT_FOUND(3000, "Order not found"),
    ORDER_STATUS_PRIORITY(3001, "Cannot update order status"),
    ORDER_FINAL_STATUS(3001, "Order is already at final status"),
    ;

    private final Integer code;
    private final String message;
}
