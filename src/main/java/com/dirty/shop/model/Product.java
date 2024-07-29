package com.dirty.shop.model;

import com.dirty.shop.base.BaseModel;
import com.dirty.shop.enums.ProductStatus;
import com.dirty.shop.enums.ProductTarget;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction(value = "is_deleted = false")
public class Product extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private ProductTarget target;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    private Double price;

    private String categoryIds; // comma separated category id

    private String avatarUrl;

}
