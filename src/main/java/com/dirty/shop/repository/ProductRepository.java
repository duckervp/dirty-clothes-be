package com.dirty.shop.repository;

import com.dirty.shop.model.Category;
import com.dirty.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
