package com.dirty.shop.service;

import com.dirty.shop.dto.request.LoginRequest;
import com.dirty.shop.dto.request.RegisterRequest;
import com.dirty.shop.dto.response.LoginResponse;


public interface AuthService {
    LoginResponse login(LoginRequest request);

    LoginResponse register(RegisterRequest request);

}
