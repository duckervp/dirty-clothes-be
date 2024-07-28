package com.dirty.shop.service;

import com.dirty.shop.dto.request.FindOrderRequest;
import com.dirty.shop.dto.request.OrderRequest;
import com.dirty.shop.model.Order;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    Page<Order> findAll(FindOrderRequest request);

    Order findById(Long id);

    Order save(OrderRequest request);

    Order update(Long id, OrderRequest request);

    String delete(Long id);

    String delete(List<Long> ids);
}
