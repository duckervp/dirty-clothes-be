package com.dirty.shop.dto.request;

import com.dirty.shop.enums.ProductTarget;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String name;

    private String description;

    private ProductTarget target;

    private Double price;

    private String categoryIds;

    List<ProductDetailRequest> productDetails;
}
