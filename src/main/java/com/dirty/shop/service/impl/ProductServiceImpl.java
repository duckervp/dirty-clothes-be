package com.dirty.shop.service.impl;

import com.dirty.shop.dto.request.ProductRequest;
import com.dirty.shop.dto.request.FindProductRequest;
import com.dirty.shop.model.Product;
import com.dirty.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Override
    public Page<Product> findAll(FindProductRequest request) {
        return null;
    }

    @Override
    public Product findById(Long id) {
        return null;
    }

    @Override
    public Product save(ProductRequest request) {
        return null;
    }

    @Override
    public Product update(Long id, ProductRequest request) {
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
