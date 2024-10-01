package com.dirty.shop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdministrativeUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String fullNameEn;

    private String shortName;

    private String shortNameEn;

    private String codeName;

    private String codeNameEn;
}
