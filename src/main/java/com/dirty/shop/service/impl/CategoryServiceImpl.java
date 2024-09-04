package com.dirty.shop.service.impl;

import com.dirty.shop.dto.request.CategoryFilterRequest;
import com.dirty.shop.dto.request.CategoryRequest;
import com.dirty.shop.dto.request.FindAllCategoryRequest;
import com.dirty.shop.dto.request.FindCategoryTreeRequest;
import com.dirty.shop.dto.response.CategoryItemResponse;
import com.dirty.shop.dto.response.CategoryResponse;
import com.dirty.shop.model.Category;
import com.dirty.shop.repository.CategoryRepository;
import com.dirty.shop.service.CategoryService;
import com.dirty.shop.utils.BusinessUtils;
import com.dirty.shop.utils.StreamUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
    public Page<Category> findAll(FindAllCategoryRequest request) {
        Sort sort = Sort.by(Sort.Direction.fromString(request.getSort()), request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPageNo(), request.getPageSize(), sort);
        return categoryRepository.findAllCategory(request, pageable);
    }

    @Override
    public List<CategoryResponse> findAllTree(FindCategoryTreeRequest request) {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        Map<Long, List<CategoryItemResponse>> mapCategoryItemResponseByParentId = new HashMap<>();

        for (Category category : categories) {
            if (Objects.nonNull(category.getParentId())) {
                if (!mapCategoryItemResponseByParentId.containsKey(category.getParentId())) {
                    mapCategoryItemResponseByParentId.put(category.getParentId(), new ArrayList<>());
                }
                mapCategoryItemResponseByParentId.get(category.getParentId())
                        .add(CategoryItemResponse.from(category));
            }
        }

        for (Category category : categories) {
            if (Objects.isNull(category.getParentId())) {
                CategoryItemResponse parent = CategoryItemResponse.from(category);
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

        List<Category> children = categoryRepository.findByParentId(category.getId());
        for (Category child : children) {
            child.setDeleted(true);
        }

        children.add(category);

        categoryRepository.saveAll(children);
        return "Delete category successful";
    }

    @Override
    public String delete(List<Long> ids) {
        List<Category> categories = categoryRepository.findAllById(ids);
        categories.forEach(e -> e.setDeleted(true));

        List<Long> parentIds = StreamUtils.toList(categories, Category::getParentId);

        List<Category> children = categoryRepository.findByParentIdIn(parentIds);
        children.forEach(child -> child.setDeleted(true));

        categories.addAll(children);

        categoryRepository.saveAll(categories);
        return "Delete list categories successful";
    }

    @Override
    public List<Category> findCategoryFilter(CategoryFilterRequest request) {
        return categoryRepository.findCategoryFilter(request);
    }
}
