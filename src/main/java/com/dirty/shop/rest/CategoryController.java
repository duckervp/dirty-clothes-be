package com.dirty.shop.rest;

import com.dirty.shop.base.Response;
import com.dirty.shop.base.WebConstants;
import com.dirty.shop.dto.request.FindCategoryRequest;
import com.dirty.shop.dto.request.CategoryRequest;
import com.dirty.shop.model.Category;
import com.dirty.shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(WebConstants.API_CATEGORY_PREFIX_V1)
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<Response<Page<Category>>> getAllCategories(FindCategoryRequest request) {
        return ResponseEntity.ok(Response.success(categoryService.findAll(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Category>> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok(Response.success(categoryService.findById(id)));
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<String>> save(@RequestBody CategoryRequest request) {
        return ResponseEntity.ok(Response.success(categoryService.save(request)));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<String>> update(@PathVariable Long id, @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(Response.success(categoryService.update(id, request)));
    }

    @DeleteMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(Response.success(categoryService.delete(id)));
    }

    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<String>> delete(@PathVariable List<Long> ids) {
        return ResponseEntity.ok(Response.success(categoryService.delete(ids)));
    }
}
