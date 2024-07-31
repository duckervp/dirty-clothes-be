package com.dirty.shop.model;

import com.dirty.shop.base.BaseModel;
import com.dirty.shop.enums.OrderStatus;
import com.dirty.shop.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "`Order`")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction(value = "is_deleted = false")
public class Order extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String code;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private Long shippingAddressId;

    private String reason;

    private Double shippingFee;

    private Double total;
}
