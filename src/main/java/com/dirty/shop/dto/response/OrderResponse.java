package com.dirty.shop.dto.response;

import com.dirty.shop.enums.OrderStatus;
import com.dirty.shop.enums.PaymentMethod;
import com.dirty.shop.model.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class OrderResponse {
    private Long id;

    private String code;

    private Long userId;

    private User user;

    private OrderStatus status;

    private PaymentMethod paymentMethod;

    private String reason;

    private Double shippingFee;

    private Double total;

    OrderItemResponse firstItem;

    private Integer totalItems;

    private Instant createdAt;

    private String createdBy;

    private String updatedBy;

    private Instant updatedAt;

    public OrderResponse(Long id, Long userId, String code, OrderStatus status, PaymentMethod paymentMethod,
                         String reason, Double shippingFee, Double total,
                         Instant createdAt, String createdBy, Instant updatedAt, String updatedBy) {
        this.id = id;
        this.userId = userId;
        this.code = code;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.reason = reason;
        this.shippingFee = shippingFee;
        this.total = total;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }
}
