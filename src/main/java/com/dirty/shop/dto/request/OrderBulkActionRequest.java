package com.dirty.shop.dto.request;

import com.dirty.shop.enums.OrderStatus;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderBulkActionRequest {
    private OrderStatus status;

    private List<Long> orderIds;
}
