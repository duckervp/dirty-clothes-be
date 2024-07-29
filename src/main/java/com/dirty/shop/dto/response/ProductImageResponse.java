package com.dirty.shop.dto.response;

import com.dirty.shop.dto.request.ProductImageRequest;
import com.dirty.shop.enums.Size;
import com.dirty.shop.model.Color;
import com.dirty.shop.model.ProductImage;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductImageResponse {
    private Long id;

    private Long colorId;

    private String colorName;

    private String colorValue;

    private String imageUrl;

    public static ProductImageResponse from(ProductImage productImage, Color color) {
        return ProductImageResponse.builder()
                .id(productImage.getId())
                .colorId(color.getId())
                .colorName(color.getName())
                .colorValue(color.getValue())
                .imageUrl(productImage.getImageUrl())
                .build();
    }
}
