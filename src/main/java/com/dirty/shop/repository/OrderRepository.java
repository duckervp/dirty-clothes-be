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

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
            SELECT NEW com.dirty.shop.dto.response.OrderResponse (
                o.id,
                o.code,
                o.status,
                o.paymentMethod,
                a,
                o.reason,
                o.shippingFee
            )
            FROM Order o
            LEFT JOIN Address a ON o.shippingAddressId = a.detailAddress
            WHERE (:#{#request.code} IS NULL OR o.code = :#{#request.code})
            AND (:#{#request.status} IS NULL OR o.status = :#{#request.code})
            """)
    Page<OrderResponse> findOrder(FindOrderRequest request, Pageable pageable);
}
