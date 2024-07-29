package com.dirty.shop.repository;

import com.dirty.shop.model.OrderDetail;
import com.dirty.shop.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findByOrderId(Long id);

    List<OrderDetail> findByOrderIdIn(List<Long> ids);
}
