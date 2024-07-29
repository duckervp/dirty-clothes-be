package com.dirty.shop.dto.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
    private Long userId;

    private String detailAddress;

    private String phone;

    private Long postalCode;

    private String note;

    private String name;
}
