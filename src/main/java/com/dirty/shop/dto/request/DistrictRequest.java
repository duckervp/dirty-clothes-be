package com.dirty.shop.dto.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DistrictRequest {
    private String code;

    private String provinceCode;
}
