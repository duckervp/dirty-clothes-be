package com.dirty.shop.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDetailResponse {
    private Long productDetailId;
    private String productName;
    private Double productPrice;
    private String avatarUrl;
    private Integer quantity;
    private Double orderDetailPrice;
}
