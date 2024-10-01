package com.dirty.shop.enums.apicode;

import com.dirty.shop.base.ApiCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AddressApiCode implements ApiCode{
    ADDRESS_NOT_FOUND(4000, "Address not found"),
    ;

    private final Integer code;
    private final String message;
}
