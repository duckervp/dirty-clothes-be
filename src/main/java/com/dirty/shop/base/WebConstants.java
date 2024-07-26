package com.dirty.shop.base;

import lombok.experimental.UtilityClass;

@UtilityClass
public class WebConstants {
    public static final String API_BASE_PREFIX_V1 = "/api/v1";

    public static final String API_AUTH_PREFIX_V1 = API_BASE_PREFIX_V1 + "/auth";

    public interface USER {
        String API_PRODUCT_PREFIX_V1 = API_BASE_PREFIX_V1 + "/product";
    }

    public interface ADMIN {
        String API_BASE_PREFIX_V1 = "/api/v1/admin";
    }
}
