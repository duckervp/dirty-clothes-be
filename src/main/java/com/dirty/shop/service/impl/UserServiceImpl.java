package com.dirty.shop.service.impl;

import com.dirty.shop.dto.request.UserRequest;
import com.dirty.shop.dto.request.FindUserRequest;
import com.dirty.shop.model.User;
import com.dirty.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    @Override
    public User save(UserRequest request) {
        return null;
    }

    @Override
    public Page<User> findAll(FindUserRequest request) {
        return null;
    }

    @Override
    public User update(Long id, UserRequest request) {
        return null;
    }

    @Override
    public String delete(Long id) {
        return "";
    }

    @Override
    public String delete(List<Long> ids) {
        return "";
    }

    @Override
    public User findById(Long id) {
        return null;
    }
}
