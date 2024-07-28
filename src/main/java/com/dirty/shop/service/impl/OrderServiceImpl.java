package com.dirty.shop.service.impl;

import com.dirty.shop.dto.request.OrderRequest;
import com.dirty.shop.dto.request.FindOrderRequest;
import com.dirty.shop.model.Order;
import com.dirty.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Override
    public Page<Order> findAll(FindOrderRequest request) {
        return null;
    }

    @Override
    public Order findById(Long id) {
        return null;
    }

    @Override
    public Order save(OrderRequest request) {
        return null;
    }

    @Override
    public Order update(Long id, OrderRequest request) {
        return null;
    }

    @Override
    public String delete(Long id) {
        return "";
    }

    @Override
    public String delete(List<Long> ids) {
        return "";
    }
}
