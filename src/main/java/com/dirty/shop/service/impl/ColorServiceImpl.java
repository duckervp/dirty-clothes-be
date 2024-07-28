package com.dirty.shop.service.impl;

import com.dirty.shop.dto.request.ColorRequest;
import com.dirty.shop.dto.request.FindColorRequest;
import com.dirty.shop.model.Color;
import com.dirty.shop.service.ColorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ColorServiceImpl implements ColorService {
    @Override
    public Color save(ColorRequest request) {
        return null;
    }

    @Override
    public Page<Color> findAll(FindColorRequest request) {
        return Page.empty();
    }

    @Override
    public Color update(Long id, ColorRequest request) {
        return null;
    }

    @Override
    public String delete(Long id) {
        return "Delete color successfully!";
    }

    @Override
    public String delete(List<Long> ids) {
        return "Delete colors successfully!";
    }

    @Override
    public Color findById(Long id) {
        return null;
    }
}
