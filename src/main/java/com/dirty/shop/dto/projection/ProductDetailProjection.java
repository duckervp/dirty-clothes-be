package com.dirty.shop.dto.projection;

import com.dirty.shop.enums.ProductStatus;

public interface ProductDetailProjection {
    Long getProductDetailId();

    String getProductName();

    Double getProductPrice();

    String getAvatarUrl();
}
