package com.dirty.shop.repository;

import com.dirty.shop.dto.response.OrderItemResponse;
import com.dirty.shop.model.OrderDetail;
import com.dirty.shop.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findByOrderId(Long id);

    List<OrderDetail> findByOrderIdIn(List<Long> ids);

    @Query("""
            SELECT NEW com.dirty.shop.dto.response.OrderItemResponse(
                od.orderId,
                p.name,
                od.price,
                od.quantity,
                c.value,
                pi.imageUrl
            ) FROM OrderDetail od
            LEFT JOIN ProductDetail pd ON od.productDetailId = pd.id
            LEFT JOIN Product p ON pd.productId = p.id
            LEFT JOIN Color c ON pd.colorId = c.id
            LEFT JOIN ProductImage pi ON pi.productId = p.id AND pi.colorId = c.id
            WHERE od.orderId IN :orderIds
            """)
    List<OrderItemResponse> findOrderItemResponse(List<Long> orderIds);
}
