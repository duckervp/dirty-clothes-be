package com.dirty.shop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindAddressRequest extends PageRequest {
    private Long userId;

    private Boolean userOnly;

    private Boolean all;
}
