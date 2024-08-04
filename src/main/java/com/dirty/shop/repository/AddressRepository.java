package com.dirty.shop.repository;

import com.dirty.shop.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("""
            SELECT add FROM Address add
            WHERE (:userId IS NULL) OR add.userId = :userId
            """)
    Page<Address> findAddress(Long userId, Pageable pageable);

    // By pass address is_deleted = false
    @Query("SELECT a FROM Order o INNER JOIN Address a ON o.shippingAddressId = a.id WHERE a.id = :id ORDER BY a.id ASC LIMIT 1")
    Optional<Address> findOrderAddressById(Long id);
}
