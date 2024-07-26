package com.dirty.shop.rest;

import com.dirty.shop.base.Response;
import com.dirty.shop.base.WebConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(WebConstants.USER.API_PRODUCT_PREFIX_V1)
public class ProductController {

    @GetMapping()
    public ResponseEntity<Response<String>> getAllProducts() {
        return ResponseEntity.ok(Response.success("OK"));
    }
}
