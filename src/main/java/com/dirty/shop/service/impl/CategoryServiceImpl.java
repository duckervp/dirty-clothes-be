package com.dirty.shop.service.impl;

import com.dirty.shop.dto.request.CategoryRequest;
import com.dirty.shop.dto.request.FindCategoryRequest;
import com.dirty.shop.model.Category;
import com.dirty.shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Override
    public Page<Category> findAll(FindCategoryRequest request) {
        return null;
    }

    @Override
    public Category findById(Long id) {
        return null;
    }

    @Override
    public Category save(CategoryRequest request) {
        return null;
    }

    @Override
    public Category update(Long id, CategoryRequest request) {
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
