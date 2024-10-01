package com.dirty.shop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuditableResponse {
    private Instant createdAt;

    private String createdBy;

    private String updatedBy;

    private Instant updatedAt;
}
