package com.dirty.shop.dto.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WardRequest {
    private String code;

    private String districtCode;
}
