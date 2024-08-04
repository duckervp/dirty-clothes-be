package com.dirty.shop.repository;

import com.dirty.shop.dto.request.FindCategoryRequest;
import com.dirty.shop.model.Category;
import com.dirty.shop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("""
            SELECT cat FROM Category cat
            WHERE (:#{#request.id} IS NULL OR cat.id = :#{#request.id})
            OR (:#{#request.name} IS NULL OR cat.name = :#{#request.name})
            OR (:#{#request.value} IS NULL OR cat.value = :#{#request.value})
            """)
    List<Category> findCategory(FindCategoryRequest request);

    @Query("SELECT c FROM Category c WHERE c.value = :value ORDER BY c.id LIMIT 1")
    Optional<Category> findByValue(String value);

    List<Category> findByParentId(Long id);
}
