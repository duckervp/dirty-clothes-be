package com.dirty.shop.service;

import com.dirty.shop.dto.request.FindCategoryRequest;
import com.dirty.shop.dto.request.CategoryRequest;
import com.dirty.shop.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    Page<Category> findAll(FindCategoryRequest request);

    Category findById(Long id);

    Category save(CategoryRequest request);

    Category update(Long id, CategoryRequest request);

    String delete(Long id);

    String delete(List<Long> ids);
}
