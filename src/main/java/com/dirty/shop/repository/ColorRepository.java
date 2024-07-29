package com.dirty.shop.repository;

import com.dirty.shop.dto.projection.ColorProjection;
import com.dirty.shop.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
    @Query("""
        SELECT
            pi.productId AS productId,
            c.id AS id,
            c.name AS name,
            c.value AS value
        FROM Color c
        INNER JOIN ProductImage pi ON c.id = pi.colorId
        WHERE pi.productId IN (:productIds)
        AND (:colorIds IS NULL OR pi.colorId IN (:colorIds))
    """)
    List<ColorProjection> findColorByProductIds(List<Long> productIds, List<Long> colorIds);

    @Query("""
        SELECT
            c
        FROM Color c
        INNER JOIN ProductImage pi ON c.id = pi.colorId
        WHERE pi.productId = :productId
    """)
    List<Color> findColorByProductId(Long productId);
}
