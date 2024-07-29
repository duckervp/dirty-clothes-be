package com.dirty.shop.service;

import com.dirty.shop.dto.request.UserRequest;
import com.dirty.shop.dto.request.FindUserRequest;
import com.dirty.shop.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    String save(UserRequest request);

    Page<User> findAll(FindUserRequest request);

    String update(Long id, UserRequest request);

    String delete(Long id);

    String delete(List<Long> ids);

    User findById(Long id);
}
