package com.dirty.shop.service;

import com.dirty.shop.dto.request.FindProductRequest;
import com.dirty.shop.dto.request.ProductRequest;
import com.dirty.shop.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Page<Product> findAll(FindProductRequest request);

    Product findById(Long id);

    Product save(ProductRequest request);

    Product update(Long id, ProductRequest request);

    String delete(Long id);

    String delete(List<Long> ids);
}
