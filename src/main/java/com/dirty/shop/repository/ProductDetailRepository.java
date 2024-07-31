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
                p.name AS productName,
                p.price AS productPrice,
                p.avatarUrl AS avatarUrl,
                c.value AS productColor,
                pi.imageUrl as imageUrl
            FROM ProductDetail pd JOIN Product p ON pd.productId = p.id
            LEFT JOIN Color c ON pd.colorId = c.id
            LEFT JOIN ProductImage pi ON pi.productId = p.id AND pi.colorId = c.id
            WHERE pd.id IN :productDetailIds
            """)
    List<ProductDetailProjection> findProductDetailByIdIn(List<Long> productDetailIds);

    List<ProductDetail> findByIdIn(List<Long> productDetailIds);
}
