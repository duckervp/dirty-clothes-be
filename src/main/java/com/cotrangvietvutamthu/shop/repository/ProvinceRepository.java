package com.dirty.shop.repository;

import com.dirty.shop.dto.request.ProvinceRequest;
import com.dirty.shop.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    @Query("SELECT p FROM Province p WHERE (:#{#request.code} IS NULL OR p.code = :#{#request.code})")
    List<Province> findProvince(ProvinceRequest request);
}
