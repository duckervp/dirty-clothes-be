package com.dirty.shop.service;

import com.dirty.shop.dto.request.FindProductRequest;
import com.dirty.shop.dto.request.ProductRequest;
import com.dirty.shop.dto.response.DetailedProductResponse;
import com.dirty.shop.dto.response.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Page<ProductResponse> findAll(FindProductRequest request);

    DetailedProductResponse findById(Long id);

    String save(ProductRequest request);

    String update(Long id, ProductRequest request);

    String delete(Long id);

    String delete(List<Long> ids);

    DetailedProductResponse findBySlug(String slug);
}
