package com.dirty.shop.repository;

import com.dirty.shop.dto.request.DistrictRequest;
import com.dirty.shop.model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    @Query("""
            SELECT d FROM District d
            WHERE (:#{#request.code} IS NULL OR d.code = :#{#request.code})
            AND (:#{#request.provinceCode} IS NULL OR d.provinceCode = :#{#request.provinceCode})
            """)
    List<District> findDistrict(DistrictRequest request);
}
