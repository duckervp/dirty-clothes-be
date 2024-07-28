package com.dirty.shop.dto.request;

import lombok.Data;

@Data
public class PageRequest {
    private Integer pageNo = 0;

    private Integer pageSize = 10;

    private String sort = "ASC";

    private String sortBy = "id";
}
