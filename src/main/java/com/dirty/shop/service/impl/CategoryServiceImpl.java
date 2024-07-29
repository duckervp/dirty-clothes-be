package com.dirty.shop.service.impl;

import com.dirty.shop.dto.request.CategoryRequest;
import com.dirty.shop.dto.request.FindCategoryRequest;
import com.dirty.shop.model.Category;
import com.dirty.shop.repository.CategoryRepository;
import com.dirty.shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Page<Category> findAll(FindCategoryRequest request) {
        Sort sort = Sort.by(Sort.Direction.fromString(request.getSort()), request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPageNo(), request.getPageSize(),sort);
        return categoryRepository.findCategory(request.getName(), pageable);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    @Override
    public String save(CategoryRequest request) {
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .parentId(request.getParentId())
                .build();

        categoryRepository.save(category);
        return "Save category successful";
    }

    @Override
    public String update(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setParentId(request.getParentId());

        categoryRepository.save(category);
        return "Update category successful";
    }

    @Override
    public String delete(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setDeleted(true);

        categoryRepository.save(category);
        return "Delete category successful";
    }

    @Override
    public String delete(List<Long> ids) {
        List<Category> categories = categoryRepository.findAllById(ids);
        categories.forEach(e -> e.setDeleted(true));

        categoryRepository.saveAll(categories);
        return "Delete list categories successful";
    }
}
