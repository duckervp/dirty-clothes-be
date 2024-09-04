package com.dirty.shop.dto.response;

import com.dirty.shop.model.Color;
import com.dirty.shop.model.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ProductImageResponse extends AuditableResponse {
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
                .createdAt(productImage.getCreatedAt())
                .createdBy(productImage.getCreatedBy())
                .updatedAt(productImage.getUpdatedAt())
                .updatedBy(productImage.getUpdatedBy())
                .build();
    }
}
