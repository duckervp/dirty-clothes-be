package com.dirty.shop.repository;

import com.dirty.shop.model.Address;
import com.dirty.shop.model.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {

    @Query("""
            SELECT col FROM Color col
            WHERE (:name IS NULL) OR col.name = :name
            """)
    Page<Color> findColor(String name, Pageable pageable);
}
