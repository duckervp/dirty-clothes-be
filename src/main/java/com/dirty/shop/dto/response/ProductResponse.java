package com.dirty.shop.dto.response;

import com.dirty.shop.dto.projection.ColorProjection;
import com.dirty.shop.enums.ProductStatus;
import com.dirty.shop.model.Color;
import com.dirty.shop.model.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductResponse {
    private String name;

    private ProductStatus status;

    private Double price;

    private List<Color> colors;

    private String avatarUrl;

    public static ProductResponse from(Product product, List<ColorProjection> colorProjections) {
        List<Color> colors = colorProjections.stream()
                .map(item -> Color.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .value(item.getValue())
                        .build()).toList();

        return ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .status(product.getStatus())
                .avatarUrl(product.getAvatarUrl())
                .colors(colors)
                .build();
    }
}
