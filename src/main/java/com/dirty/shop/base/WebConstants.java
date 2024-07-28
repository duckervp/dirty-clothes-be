package com.dirty.shop.base;

import lombok.experimental.UtilityClass;

@UtilityClass
public class WebConstants {
    public static final String API_BASE_PREFIX_V1 = "/api/v1";
    public static final String API_AUTH_PREFIX_V1 = API_BASE_PREFIX_V1 + "/auth";
    public static final String API_USER_PREFIX_V1 = API_BASE_PREFIX_V1 + "/user";
    public static final String API_PRODUCT_PREFIX_V1 = API_BASE_PREFIX_V1 + "/product";
    public static final String API_COLOR_PREFIX_V1 = API_BASE_PREFIX_V1 + "/color";
    public static final String API_ADDRESS_PREFIX_V1 = API_BASE_PREFIX_V1 + "/address";
    public static final String API_ORDER_PREFIX_V1 = API_BASE_PREFIX_V1 + "/order";
    public static final String API_FILE_PREFIX_V1 = API_BASE_PREFIX_V1 + "/file";
    public static final String API_CATEGORY_PREFIX_V1 = API_BASE_PREFIX_V1 + "/category";


    public static final String UPLOADED_FILE_PREFIX = "/uploads";
}
