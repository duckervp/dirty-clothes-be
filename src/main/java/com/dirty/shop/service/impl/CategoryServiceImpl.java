package com.dirty.shop.service.impl;

import com.dirty.shop.dto.request.CategoryRequest;
import com.dirty.shop.dto.request.FindCategoryRequest;
import com.dirty.shop.dto.response.CategoryItemResponse;
import com.dirty.shop.dto.response.CategoryResponse;
import com.dirty.shop.model.Category;
import com.dirty.shop.repository.CategoryRepository;
import com.dirty.shop.service.CategoryService;
import com.dirty.shop.utils.BusinessUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> findAll(FindCategoryRequest request) {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        Map<Long, List<CategoryItemResponse>> mapCategoryItemResponseByParentId = new HashMap<>();

        for (Category category : categories) {
            category.setValue(BusinessUtils.genSlug(category.getName()));
            if (Objects.nonNull(category.getParentId())) {
                if (!mapCategoryItemResponseByParentId.containsKey(category.getParentId())) {
                    mapCategoryItemResponseByParentId.put(category.getParentId(), new ArrayList<>());
                }
                mapCategoryItemResponseByParentId.get(category.getParentId())
                        .add(CategoryItemResponse.builder()
                                .id(category.getId())
                                .name(category.getName())
                                .value(category.getValue())
                                .parentId(category.getParentId())
                                .build());
            }
        }
        categoryRepository.saveAll(categories);

        for (Category category : categories) {
            if (Objects.isNull(category.getParentId())) {
                CategoryItemResponse parent = CategoryItemResponse.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .value(category.getValue())
                        .build();
                categoryResponses.add(CategoryResponse.builder()
                        .parent(parent)
                        .children(mapCategoryItemResponseByParentId.get(parent.getId()))
                        .build());
            }
        }

        if (Objects.nonNull(request.getParentId())) {
            categoryResponses = categoryResponses.stream()
                    .filter(item -> request.getParentId().equals(item.getParent().getId())).toList();
        } else if (Objects.nonNull(request.getParentValue())) {
            categoryResponses = categoryResponses.stream()
                    .filter(item -> request.getParentValue().equals(item.getParent().getValue())).toList();
        }

        return categoryResponses;
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    @Override
    public String save(CategoryRequest request) {
        Category category = Category.builder()
                .name(request.getName())
                .value(Objects.nonNull(request.getValue()) ? request.getValue() : BusinessUtils.genSlug(request.getName()))
                .parentId(request.getParentId())
                .build();

        categoryRepository.save(category);
        return "Save category successful";
    }

    @Override
    public String update(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(request.getName());
        category.setValue(Objects.nonNull(request.getValue()) ? request.getValue() : BusinessUtils.genSlug(request.getName()));
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
