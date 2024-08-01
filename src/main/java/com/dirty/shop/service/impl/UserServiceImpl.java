package com.dirty.shop.service.impl;

import com.dirty.shop.dto.request.UserNameRequest;
import com.dirty.shop.dto.request.UserRequest;
import com.dirty.shop.dto.request.FindUserRequest;
import com.dirty.shop.enums.apicode.AuthApiCode;
import com.dirty.shop.exception.ApiException;
import com.dirty.shop.model.User;
import com.dirty.shop.repository.UserRepository;
import com.dirty.shop.service.UserService;
import com.dirty.shop.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public String updateName(UserNameRequest request) {
        if (!StringUtils.hasText(request.getName())) {
            throw new ApiException(AuthApiCode.NAME_IS_REQUIRED);
        }

        User user = userRepository.findById(AuthUtils.currentUserId())
                .orElseThrow(() -> new ApiException(AuthApiCode.USER_NOT_FOUND));

        user.setName(request.getName());
        userRepository.save(user);
        return "Update user name successfully!";
    }

    @Override
    public String save(UserRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .role(request.getRole())
                .status(request.getStatus())
                .build();

        userRepository.save(user);
        return "Save user successful";
    }

    @Override
    public Page<User> findAll(FindUserRequest request) {
        Sort sort = Sort.by(Sort.Direction.fromString(request.getSort()), request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPageNo(), request.getPageSize(),sort);
        return userRepository.findUser(request, pageable);
    }

    @Override
    public String update(Long id, UserRequest request) {
        User user = userRepository.findById(id).orElseThrow();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setRole(request.getRole());
        user.setStatus(request.getStatus());

        userRepository.save(user);
        return "Update user successful";
    }

    @Override
    public String delete(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        user.setDeleted(true);

        userRepository.save(user);
        return "Delete user successful";
    }

    @Override
    public String delete(List<Long> ids) {
        List<User> users = userRepository.findAllById(ids);
        for (User user : users){
            user.setDeleted(true);
        }

        userRepository.saveAll(users);
        return "Delete users successful";
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }
}
