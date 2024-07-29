package com.dirty.shop.dto.projection;

import com.dirty.shop.enums.ProductStatus;

public interface ProductProjection {
    String getName();

    ProductStatus getStatus();

    Double getPrice();

    String getAvatarUrl();
}
