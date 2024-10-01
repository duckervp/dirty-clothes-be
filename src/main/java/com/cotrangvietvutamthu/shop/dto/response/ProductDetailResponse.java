package com.dirty.shop.dto.response;

import com.dirty.shop.model.ProductDetail;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ProductDetailResponse extends AuditableResponse {
    private Long id;

    private Long colorId;

    private String size;

    private Long inventory;

    private Long sold;

    public static ProductDetailResponse from(ProductDetail productDetail) {
        return ProductDetailResponse.builder()
                .id(productDetail.getId())
                .colorId(productDetail.getColorId())
                .size(productDetail.getSize().getValue())
                .inventory(productDetail.getInventory())
                .sold(productDetail.getSold())
                .createdAt(productDetail.getCreatedAt())
                .createdBy(productDetail.getCreatedBy())
                .updatedAt(productDetail.getUpdatedAt())
                .updatedBy(productDetail.getUpdatedBy())
                .build();
    }
}
