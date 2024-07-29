package com.dirty.shop.repository;

import com.dirty.shop.model.Category;
import com.dirty.shop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("""
            SELECT cat FROM Category cat
            WHERE (:name IS NULL) OR cat.name = :name
            """)
    Page<Category> findCategory(String name, Pageable pageable);
}
