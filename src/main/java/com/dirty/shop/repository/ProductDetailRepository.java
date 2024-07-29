package com.dirty.shop.repository;

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
}
