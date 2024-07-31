package com.dirty.shop.service;

import com.dirty.shop.dto.request.FindOrderRequest;
import com.dirty.shop.dto.request.OrderRequest;
import com.dirty.shop.dto.response.OrderDetailResponse;
import com.dirty.shop.dto.response.OrderResponse;
import com.dirty.shop.model.Order;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    Page<OrderResponse> findAll(FindOrderRequest request);

    OrderDetailResponse findDetailById(Long id);

    OrderDetailResponse findDetailByCode(String code);

    String save(OrderRequest request);

    String update(Long id, OrderRequest request);

    String delete(Long id);

    String delete(List<Long> ids);

    Order findById(Long id);
}
