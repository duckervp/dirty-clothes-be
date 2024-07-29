package com.dirty.shop.dto.request;

import com.dirty.shop.enums.OrderStatus;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Long shippingAddressId;

    private String receiverName;

    private String phone;

    private String shippingAddress;

    private OrderStatus orderStatus;

    private String note;

    private Long postalCode;

    List<OrderDetailRequest> orderDetails;
}
