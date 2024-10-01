package com.dirty.shop.repository;

import com.dirty.shop.dto.request.CategoryFilterRequest;
import com.dirty.shop.dto.request.FindAllCategoryRequest;
import com.dirty.shop.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE c.value = :value ORDER BY c.id LIMIT 1")
    Optional<Category> findByValue(String value);

    List<Category> findByParentId(Long id);

    List<Category> findByParentIdIn(Collection<Long> ids);

    @Query("""
            SELECT c FROM Category c
            WHERE (:#{#request.name} IS NULL OR LOWER(c.name) LIKE CONCAT('%', LOWER(:#{#request.name}), '%'))
            AND (:#{#request.parentId} IS NULL OR c.parentId = :#{#request.parentId})
            """)
    Page<Category> findAllCategory(FindAllCategoryRequest request, Pageable pageable);

    @Query("""
            SELECT c FROM Category c
            WHERE (:#{#request.name} IS NULL OR LOWER(c.name) LIKE CONCAT('%', LOWER(:#{#request.name}), '%'))
            AND (:#{#request.parent} IS NULL
                OR (:#{#request.parent} = FALSE AND c.parentId IS NOT NULL)
                OR (:#{#request.parent} = TRUE AND c.parentId IS NULL)
            )
            """)
    List<Category> findCategoryFilter(CategoryFilterRequest request);
}
