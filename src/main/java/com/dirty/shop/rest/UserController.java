package com.dirty.shop.rest;

import com.dirty.shop.base.Response;
import com.dirty.shop.base.WebConstants;
import com.dirty.shop.dto.request.FindUserRequest;
import com.dirty.shop.dto.request.UserRequest;
import com.dirty.shop.model.User;
import com.dirty.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(WebConstants.API_USER_PREFIX_V1)
public class UserController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<Response<Page<User>>> getAllUsers(FindUserRequest request) {
        return ResponseEntity.ok(Response.success(userService.findAll(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<User>> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(Response.success(userService.findById(id)));
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<User>> save(@RequestBody UserRequest request) {
        return ResponseEntity.ok(Response.success(userService.save(request)));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<User>> update(@PathVariable Long id, @RequestBody UserRequest request) {
        return ResponseEntity.ok(Response.success(userService.update(id, request)));
    }

    @DeleteMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(Response.success(userService.delete(id)));
    }

    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<String>> delete(@PathVariable List<Long> ids) {
        return ResponseEntity.ok(Response.success(userService.delete(ids)));
    }
}
