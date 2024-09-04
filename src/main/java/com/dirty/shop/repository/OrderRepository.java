package com.dirty.shop.repository;

import com.dirty.shop.dto.request.FindOrderRequest;
import com.dirty.shop.dto.response.OrderResponse;
import com.dirty.shop.model.Order;
import com.dirty.shop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
            SELECT NEW com.dirty.shop.dto.response.OrderResponse (
                o.id,
                o.userId,
                o.code,
                o.status,
                o.paymentMethod,
                o.reason,
                o.shippingFee,
                o.total,
                o.createdAt,
                o.createdBy,
                o.updatedAt,
                o.updatedBy
            )
            FROM Order o
            WHERE (:#{#request.code} IS NULL OR o.code = :#{#request.code})
            AND (:#{#request.status} IS NULL OR o.status = :#{#request.status})
            AND (:#{#request.userId} IS NULL OR o.userId = :#{#request.userId})
            """)
    Page<OrderResponse> findOrder(FindOrderRequest request, Pageable pageable);

    Optional<Order> findByCode(String code);
}
