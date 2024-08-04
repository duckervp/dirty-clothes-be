package com.dirty.shop.config;

import com.dirty.shop.config.serialize.PageableSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;

@Configuration
@RequiredArgsConstructor
public class JacksonConfig {
    private final ObjectMapper objectMapper;

    @PostConstruct
    private void addPageableSerializer() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Pageable.class, new PageableSerializer());
        objectMapper.registerModule(module);
    }
}
