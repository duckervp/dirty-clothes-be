package com.dirty.shop.repository;

import com.dirty.shop.dto.projection.ProductDetailProjection;
import com.dirty.shop.enums.Size;
import com.dirty.shop.model.Category;
import com.dirty.shop.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    List<ProductDetail> findByProductId(Long productId);

    List<ProductDetail> findByProductIdIn(List<Long> productIds);

    @Query("SELECT pd.productId FROM ProductDetail pd WHERE pd.productId IN (:productIds) AND pd.size IN (:sizes)")
    List<Long> findProductBySize(List<Long> productIds, List<Size> sizes);

    @Query("""
            SELECT
                pd.id AS productDetailId,
                pd.size AS productSize,
                p.name AS productName,
                p.price AS productPrice,
                p.avatarUrl AS avatarUrl,
                c.value AS productColor,
                pi3.imageUrl AS imageUrl,
                p.slug AS slug
            FROM ProductDetail pd JOIN Product p ON pd.productId = p.id
            LEFT JOIN Color c ON pd.colorId = c.id
            LEFT JOIN (
                SELECT
                    pi2.productId AS productId,
                    pi2.colorId AS colorId,
                    pi2.imageUrl AS imageUrl
                FROM (
                    SELECT
                        pi.productId AS productId,
                        pi.colorId AS colorId,
                        pi.imageUrl AS imageUrl,
                        ROW_NUMBER() OVER (PARTITION BY pi.productId, pi.colorId) AS rn
                    FROM ProductImage pi
                    ORDER BY pi.id
                ) pi2
                WHERE pi2.rn = 1
            ) pi3 ON pi3.productId = p.id AND pi3.colorId = c.id
            WHERE pd.id IN :productDetailIds
            """)
    List<ProductDetailProjection> findProductDetailByIdIn(List<Long> productDetailIds);

    List<ProductDetail> findByIdIn(List<Long> productDetailIds);
}
