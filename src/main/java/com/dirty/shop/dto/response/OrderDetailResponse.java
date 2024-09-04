package com.dirty.shop.dto.response;

import com.dirty.shop.enums.OrderStatus;
import com.dirty.shop.enums.PaymentMethod;
import com.dirty.shop.model.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class OrderDetailResponse extends AuditableResponse{
    private Long id;

    private String code;

    private OrderStatus status;

    private PaymentMethod paymentMethod;

    private Address address;

    private String reason;

    private Double shippingFee;

    private Double total;

    List<OrderItemResponse> orderItems;
}
