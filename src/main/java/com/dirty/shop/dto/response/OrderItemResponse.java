package com.dirty.shop.dto.response;

import com.dirty.shop.enums.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse {
    @JsonIgnore
    private Long orderId;

    private Long orderDetailId;

    private String productName;

    private Double price;

    private Integer quantity;

    private String color; // color value

    private String imageUrl;

    private String size;

    private String slug;

    public OrderItemResponse(Long orderId, Long orderDetailId, String productName, Double price,
                             Integer quantity, String color, String imageUrl, Size size, String slug) {
        this.orderId = orderId;
        this.orderDetailId = orderDetailId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.color = color;
        this.imageUrl = imageUrl;
        this.size = size.getValue();
        this.slug = slug;
    }
}
