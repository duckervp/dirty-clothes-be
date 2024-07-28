package com.dirty.shop.rest;

import com.dirty.shop.base.Response;
import com.dirty.shop.base.WebConstants;
import com.dirty.shop.dto.request.ProductRequest;
import com.dirty.shop.dto.request.FindProductRequest;
import com.dirty.shop.model.Product;
import com.dirty.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(WebConstants.API_PRODUCT_PREFIX_V1)
public class ProductController {
    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<Response<Page<Product>>> getAllProducts(FindProductRequest request) {
        return ResponseEntity.ok(Response.success(productService.findAll(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Product>> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(Response.success(productService.findById(id)));
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<Product>> save(@RequestBody ProductRequest request) {
        return ResponseEntity.ok(Response.success(productService.save(request)));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<Product>> update(@PathVariable Long id, @RequestBody ProductRequest request) {
        return ResponseEntity.ok(Response.success(productService.update(id, request)));
    }

    @DeleteMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(Response.success(productService.delete(id)));
    }

    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<String>> delete(@PathVariable List<Long> ids) {
        return ResponseEntity.ok(Response.success(productService.delete(ids)));
    }
}
