package com.dirty.shop.service;

import com.dirty.shop.dto.request.ColorRequest;
import com.dirty.shop.dto.request.FindColorRequest;
import com.dirty.shop.model.Color;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ColorService {
    Color save(ColorRequest request);

    Page<Color> findAll(FindColorRequest request);

    Color update(Long id, ColorRequest request);

    String delete(Long id);

    String delete(List<Long> ids);

    Color findById(Long id);
}
