package com.dirty.shop.dto.response;

import com.dirty.shop.enums.OrderStatus;
import com.dirty.shop.enums.PaymentMethod;
import com.dirty.shop.model.Address;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderResponse {
    private Long id;

    private String code;

    private OrderStatus status;

    private PaymentMethod paymentMethod;

    private Address address;

    private String reason;

    private Double shippingFee;

    private Double total;

    OrderItemResponse firstItem;
}
