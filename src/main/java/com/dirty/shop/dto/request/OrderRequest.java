package com.dirty.shop.dto.request;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String name;

    private String value;

    private String description;

    List<OrderDetailRequest> orderDetails;
}
