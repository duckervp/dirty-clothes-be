package com.dirty.shop.dto.request;

import com.dirty.shop.enums.Size;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailRequest {
    private Size size;

    private Long inventory;

    private Long sold;

    private List<ProductImageRequest> images;
}
