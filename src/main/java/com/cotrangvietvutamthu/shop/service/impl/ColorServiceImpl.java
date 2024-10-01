package com.dirty.shop.service.impl;

import com.dirty.shop.dto.request.ColorRequest;
import com.dirty.shop.dto.request.FindColorRequest;
import com.dirty.shop.model.Color;
import com.dirty.shop.repository.ColorRepository;
import com.dirty.shop.service.ColorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ColorServiceImpl implements ColorService {
    private final ColorRepository colorRepository;

    @Override
    public String save(ColorRequest request) {
        Color color = Color.builder()
                .name(request.getName())
                .value(request.getValue())
                .description(request.getDescription())
                .build();

        colorRepository.save(color);
        return "Save color successful";
    }

    @Override
    public Page<Color> findAll(FindColorRequest request) {
        Sort sort = Sort.by(Sort.Direction.fromString(request.getSort()), request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPageNo(), request.getPageSize(), sort);

        if (Boolean.TRUE.equals(request.getAll())) {
            pageable = Pageable.unpaged(sort);
        }

        return colorRepository.findColor(request.getName(), pageable);
    }

    @Override
    public String update(Long id, ColorRequest request) {
        Color color = colorRepository.findById(id).orElseThrow();
        color.setName(request.getName());
        color.setDescription(request.getDescription());
        color.setValue(request.getValue());

        colorRepository.save(color);
        return "Update color successful";
    }

    @Override
    public String delete(Long id) {
        Color color = colorRepository.findById(id).orElseThrow();
        color.setDeleted(true);

        colorRepository.save(color);
        return "Delete color successfully!";
    }

    @Override
    public String delete(List<Long> ids) {
        List<Color> colors = colorRepository.findAllById(ids);
        colors.forEach(e -> e.setDeleted(true));

        colorRepository.saveAll(colors);
        return "Delete colors successfully!";
    }

    @Override
    public Color findById(Long id) {
        return colorRepository.findById(id).orElseThrow();
    }
}
