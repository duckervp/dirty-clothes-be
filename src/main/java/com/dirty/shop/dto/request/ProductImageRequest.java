package com.dirty.shop.dto.request;

import com.dirty.shop.enums.ProductTarget;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageRequest {
    private Long colorId;

    private String imageUrl;
}
