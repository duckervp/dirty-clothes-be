package com.dirty.shop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Size {
    FREE_SIZE("Free Size"),
    SIZE_S("Size S"),
    SIZE_M("Size M"),
    SIZE_L("Size L"),
    SIZE_XL("Size XL"),
    SIZE_XXL("Size XXL"),
    SIZE_3XL("Size 3XL"),
    SIZE_4XL("Size 4XL")
    ;

    private final String value;
}
