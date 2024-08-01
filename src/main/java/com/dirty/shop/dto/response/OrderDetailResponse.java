package com.dirty.shop.dto.response;

import com.dirty.shop.enums.OrderStatus;
import com.dirty.shop.enums.PaymentMethod;
import com.dirty.shop.model.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class OrderDetailResponse {
    private Long id;

    private String code;

    private OrderStatus status;

    private PaymentMethod paymentMethod;

    private Address address;

    private String reason;

    private Double shippingFee;

    private Double total;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    private Instant createdAt;

    List<OrderItemResponse> orderItems;
}
