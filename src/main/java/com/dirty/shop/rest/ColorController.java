package com.dirty.shop.rest;

import com.dirty.shop.base.Response;
import com.dirty.shop.base.WebConstants;
import com.dirty.shop.dto.request.ColorRequest;
import com.dirty.shop.dto.request.FindColorRequest;
import com.dirty.shop.model.Address;
import com.dirty.shop.model.Color;
import com.dirty.shop.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(WebConstants.API_COLOR_PREFIX_V1)
public class ColorController {
    private final ColorService colorService;

    @GetMapping()
    public ResponseEntity<Response<Page<Color>>> getAllColors(FindColorRequest request) {
        return ResponseEntity.ok(Response.success(colorService.findAll(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Color>> getColor(@PathVariable Long id) {
        return ResponseEntity.ok(Response.success(colorService.findById(id)));
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<Color>> save(@RequestBody ColorRequest request) {
        return ResponseEntity.ok(Response.success(colorService.save(request)));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<Color>> update(@PathVariable Long id, @RequestBody ColorRequest request) {
        return ResponseEntity.ok(Response.success(colorService.update(id, request)));
    }

    @DeleteMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(Response.success(colorService.delete(id)));
    }

    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<String>> delete(@PathVariable List<Long> ids) {
        return ResponseEntity.ok(Response.success(colorService.delete(ids)));
    }
}
