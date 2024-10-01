package com.dirty.shop.dto.response;

import com.dirty.shop.model.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class CategoryItemResponse extends AuditableResponse {
    private Long id;

    private String name;

    private String value;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long parentId;

    public static CategoryItemResponse from(Category category) {
        return CategoryItemResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .value(category.getValue())
                .parentId(category.getParentId())
                .createdAt(category.getCreatedAt())
                .createdBy(category.getCreatedBy())
                .updatedAt(category.getUpdatedAt())
                .updatedBy(category.getUpdatedBy())
                .build();
    }
}
