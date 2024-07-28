package com.dirty.shop.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindColorRequest extends PageRequest {
    private String name;
}
