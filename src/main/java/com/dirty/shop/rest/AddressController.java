package com.dirty.shop.rest;

import com.dirty.shop.base.Response;
import com.dirty.shop.base.WebConstants;
import com.dirty.shop.dto.request.AddressRequest;
import com.dirty.shop.dto.request.FindAddressRequest;
import com.dirty.shop.model.Address;
import com.dirty.shop.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(WebConstants.API_ADDRESS_PREFIX_V1)
public class AddressController {
    private final AddressService addressService;

    @GetMapping()
    public ResponseEntity<Response<Page<Address>>> getAllAddresses(FindAddressRequest request) {
        return ResponseEntity.ok(Response.success(addressService.findAll(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Address>> getAddress(@PathVariable Long id) {
        return ResponseEntity.ok(Response.success(addressService.findById(id)));
    }

    @PostMapping()
    public ResponseEntity<Response<Address>> save(@RequestBody AddressRequest request) {
        return ResponseEntity.ok(Response.success(addressService.save(request)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Response<String>> update(@PathVariable Long id, @RequestBody AddressRequest request) {
        return ResponseEntity.ok(Response.success(addressService.update(id, request)));
    }

    @DeleteMapping()
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(Response.success(addressService.delete(id)));
    }

    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<String>> delete(@PathVariable List<Long> ids) {
        return ResponseEntity.ok(Response.success(addressService.delete(ids)));
    }
}
