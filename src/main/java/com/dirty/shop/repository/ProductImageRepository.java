package com.dirty.shop.repository;

import com.dirty.shop.model.ProductDetail;
import com.dirty.shop.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

}
