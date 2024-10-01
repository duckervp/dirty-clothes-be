package com.dirty.shop.dto.request;

import com.dirty.shop.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindOrderRequest extends PageRequest {
    private String code;

    private OrderStatus status;

    private Boolean userOnly;

    private Long userId;

    private String username;
}
