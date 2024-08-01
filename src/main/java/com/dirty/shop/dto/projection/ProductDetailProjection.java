package com.dirty.shop.dto.projection;

import com.dirty.shop.enums.Size;

public interface ProductDetailProjection {
    Long getProductDetailId();

    String getProductName();

    Double getProductPrice();

    Size getProductSize();

    String getAvatarUrl();

    String getProductColor();

    String getImageUrl();
}
