package com.dirty.shop.repository;

import com.dirty.shop.dto.request.WardRequest;
import com.dirty.shop.model.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardRepository extends JpaRepository<Ward, Long> {
    @Query("""
            SELECT w FROM Ward w
            WHERE (:#{#request.code} IS NULL OR w.code = :#{#request.code})
            AND (:#{#request.districtCode} IS NULL OR w.districtCode = :#{#request.districtCode})
            """)
    List<Ward> findWard(WardRequest request);
}
