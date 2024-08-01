package com.dirty.shop.dto.response;

import com.dirty.shop.enums.OrderStatus;
import com.dirty.shop.enums.PaymentMethod;
import com.dirty.shop.model.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;

    private String code;

    private OrderStatus status;

    private PaymentMethod paymentMethod;

    private String reason;

    private Double shippingFee;

    private Double total;

    OrderItemResponse firstItem;

    private Integer totalItems;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    private Instant createdAt;

    public OrderResponse(Long id, String code, OrderStatus status, PaymentMethod paymentMethod, String reason, Double shippingFee, Double total, Instant createdAt) {
        this.id = id;
        this.code = code;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.reason = reason;
        this.shippingFee = shippingFee;
        this.total = total;
        this.createdAt = createdAt;
    }
}
