package com.dirty.shop.model;

import com.dirty.shop.base.BaseModel;
import com.dirty.shop.enums.Size;
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
public class ProductDetail extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Long colorId;

    @Enumerated(EnumType.STRING)
    private Size size;

    private Long inventory;

    private Long sold;
}
