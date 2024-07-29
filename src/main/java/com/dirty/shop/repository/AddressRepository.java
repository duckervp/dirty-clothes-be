package com.dirty.shop.repository;

import com.dirty.shop.model.Address;
import com.dirty.shop.model.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("""
            SELECT add FROM Address add
            WHERE (:userId IS NULL) OR add.userId = :userId
            """)
    Page<Address> findAddress(Long userId, Pageable pageable);
}
