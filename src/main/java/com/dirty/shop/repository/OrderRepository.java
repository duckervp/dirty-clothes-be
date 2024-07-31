package com.dirty.shop.repository;

import com.dirty.shop.dto.request.FindOrderRequest;
import com.dirty.shop.model.Order;
import com.dirty.shop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
            SELECT o FROM Order o
            WHERE (:#{#request.code} IS NULL OR o.code = :#{#request.code})
            AND (:#{#request.status} IS NULL OR o.status = :#{#request.code})
            """)
    Page<Order> findOrder(FindOrderRequest request, Pageable pageable);
}
