package com.dirty.shop.dto.request;

import com.dirty.shop.enums.ProductStatus;
import com.dirty.shop.enums.ProductTarget;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private Long id;

    private String name;

    private String description;

    private ProductTarget target;

    private ProductStatus status;

    private Double price;

    private List<Long> categoryIds;

    List<ProductDetailRequest> productDetails;

    private List<ProductImageRequest> images;

    private String avatarUrl;
}
