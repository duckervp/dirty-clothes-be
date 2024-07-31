package com.dirty.shop.rest;

import com.dirty.shop.base.Response;
import com.dirty.shop.base.WebConstants;
import com.dirty.shop.dto.request.FindOrderRequest;
import com.dirty.shop.dto.request.OrderRequest;
import com.dirty.shop.dto.response.OrderDetailResponse;
import com.dirty.shop.dto.response.OrderResponse;
import com.dirty.shop.model.Order;
import com.dirty.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(WebConstants.API_ORDER_PREFIX_V1)
public class OrderController {
    private final OrderService orderService;

    @GetMapping()
    public ResponseEntity<Response<Page<OrderResponse>>> getAllOrders(FindOrderRequest request) {
        return ResponseEntity.ok(Response.success(orderService.findAll(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<OrderDetailResponse>> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(Response.success(orderService.findDetailById(id)));
    }

    @GetMapping("/{code}/detail")
    public ResponseEntity<Response<OrderDetailResponse>> getOrderByCode(@PathVariable String code) {
        return ResponseEntity.ok(Response.success(orderService.findDetailByCode(code)));
    }

    @PostMapping()
    public ResponseEntity<Response<String>> save(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(Response.success(orderService.save(request)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Response<String>> update(@PathVariable Long id, @RequestBody OrderRequest request) {
        return ResponseEntity.ok(Response.success(orderService.update(id, request)));
    }

    @DeleteMapping()
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(Response.success(orderService.delete(id)));
    }

    @DeleteMapping("/{ids}")
    public ResponseEntity<Response<String>> delete(@PathVariable List<Long> ids) {
        return ResponseEntity.ok(Response.success(orderService.delete(ids)));
    }
}
