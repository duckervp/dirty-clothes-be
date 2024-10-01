package com.dirty.shop.dto.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageRequest {
    private Long id;

    private Long colorId;

    private String imageUrl;
}
