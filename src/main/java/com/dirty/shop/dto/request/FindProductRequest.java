package com.dirty.shop.dto.request;

import com.dirty.shop.enums.ProductTarget;
import com.dirty.shop.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindProductRequest extends PageRequest {
    private String name;

    private Double priceFrom;

    private Double priceTo;

    private List<ProductTarget> targets;

    private List<Long> categoryIds;

    private List<Long> colorIds;

    private List<Size> sizes;
}
