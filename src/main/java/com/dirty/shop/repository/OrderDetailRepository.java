package com.dirty.shop.repository;

import com.dirty.shop.dto.response.OrderItemResponse;
import com.dirty.shop.model.OrderDetail;
import com.dirty.shop.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderIdIn(Collection<Long> orderIds);

    List<OrderDetail> findByOrderId(Long id);

    List<OrderDetail> findByOrderIdIn(List<Long> ids);

    @Query("""
            SELECT NEW com.dirty.shop.dto.response.OrderItemResponse(
                od.orderId,
                od.id,
                p.name,
                od.price,
                od.quantity,
                c.value,
                pi3.imageUrl,
                pd.size,
                p.slug
            ) FROM OrderDetail od
            LEFT JOIN ProductDetail pd ON od.productDetailId = pd.id
            LEFT JOIN Product p ON pd.productId = p.id
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
            WHERE od.orderId IN :orderIds
            """)
    List<OrderItemResponse> findOrderItemResponse(List<Long> orderIds);
}
