package com.dirty.shop.repository;

import com.dirty.shop.model.Address;
import com.dirty.shop.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
