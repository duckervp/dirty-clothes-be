package com.dirty.shop.dto.request;

import com.dirty.shop.enums.OrderStatus;
import com.dirty.shop.enums.PaymentMethod;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Long shippingAddressId;

    private OrderStatus orderStatus;

    List<OrderDetailRequest> orderDetails;

    private Double shippingFee;

    private Double total;

    private PaymentMethod paymentMethod;
}

