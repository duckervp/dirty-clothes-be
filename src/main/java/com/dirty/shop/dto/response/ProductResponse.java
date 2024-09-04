package com.dirty.shop.dto.response;

import com.dirty.shop.dto.projection.ColorProjection;
import com.dirty.shop.enums.ProductStatus;
import com.dirty.shop.model.Color;
import com.dirty.shop.model.Product;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ProductResponse extends AuditableResponse {
    private Long id;

    private String name;

    private ProductStatus status;

    private Double price;

    private Double salePrice;

    private List<Color> colors;

    private String avatarUrl;

    private String slug;

    public static ProductResponse from(Product product, List<ColorProjection> colorProjections) {
        List<Color> colors = colorProjections.stream()
                .map(item -> Color.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .value(item.getValue())
                        .build()).toList();

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .status(product.getStatus())
                .avatarUrl(product.getAvatarUrl())
                .colors(colors)
                .salePrice(product.getSalePrice())
                .slug(product.getSlug())
                .createdAt(product.getCreatedAt())
                .createdBy(product.getCreatedBy())
                .updatedAt(product.getUpdatedAt())
                .updatedBy(product.getUpdatedBy())
                .build();
    }
}
