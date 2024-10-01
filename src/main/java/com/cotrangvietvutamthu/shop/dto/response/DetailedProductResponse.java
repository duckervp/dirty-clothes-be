package com.dirty.shop.dto.response;

import com.dirty.shop.enums.ProductStatus;
import com.dirty.shop.enums.ProductTarget;
import com.dirty.shop.model.Category;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class DetailedProductResponse extends AuditableResponse {
    private Long id;

    private String name;

    private String description;

    private ProductTarget target;

    private ProductStatus status;

    private Double price;

    private Double salePrice;

    private List<Category> categories;

    private List<ProductDetailResponse> productDetails;

    private List<ProductImageResponse> images;

    private String slug;

    private String avatarUrl;
}
