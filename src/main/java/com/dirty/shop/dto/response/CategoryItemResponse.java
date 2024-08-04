package com.dirty.shop.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryItemResponse {
    private Long id;

    private String name;

    private String value;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long parentId;
}
