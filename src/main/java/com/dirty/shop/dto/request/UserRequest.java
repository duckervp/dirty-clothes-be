package com.dirty.shop.dto.request;

import com.dirty.shop.enums.Role;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String email;

    private String password;

    private String name;

    private Role role;

    private Boolean status;

    private String avatarUrl;
}
