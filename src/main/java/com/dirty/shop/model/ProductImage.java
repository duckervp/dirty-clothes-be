package com.dirty.shop.model;

import com.dirty.shop.base.BaseModel;
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
public class ProductImage extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productDetailId;

    private Long colorId;

    private String imageUrl;
}
