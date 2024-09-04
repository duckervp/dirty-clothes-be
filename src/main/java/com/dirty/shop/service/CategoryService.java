package com.dirty.shop.service;

import com.dirty.shop.dto.request.CategoryFilterRequest;
import com.dirty.shop.dto.request.FindAllCategoryRequest;
import com.dirty.shop.dto.request.FindCategoryTreeRequest;
import com.dirty.shop.dto.request.CategoryRequest;
import com.dirty.shop.dto.response.CategoryResponse;
import com.dirty.shop.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    Page<Category> findAll(FindAllCategoryRequest request);

    List<CategoryResponse> findAllTree(FindCategoryTreeRequest request);

    Category findById(Long id);

    String save(CategoryRequest request);

    String update(Long id, CategoryRequest request);

    String delete(Long id);

    String delete(List<Long> ids);

    List<Category> findCategoryFilter(CategoryFilterRequest request);
}
