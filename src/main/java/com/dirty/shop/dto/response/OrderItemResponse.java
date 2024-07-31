package com.dirty.shop.dto.response;

import com.dirty.shop.enums.OrderStatus;
import com.dirty.shop.enums.PaymentMethod;
import com.dirty.shop.model.Address;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderItemResponse {
    private String productName;

    private Double productPrice;

    private Integer productQuantity;

    private String productColor; // color value

    private String productImageUrl;
}
