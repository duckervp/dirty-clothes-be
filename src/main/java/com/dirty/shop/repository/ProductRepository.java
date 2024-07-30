package com.dirty.shop.repository;

import com.dirty.shop.dto.projection.ProductProjection;
import com.dirty.shop.dto.request.FindProductRequest;
import com.dirty.shop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("""
        SELECT
            p.name AS name,
            p.status AS status,
            p.price AS price,
            p.avatarUrl AS avatarUrl
        FROM Product p
        WHERE (:#{#request.name} IS NULL OR LOWER(p.name) LIKE CONCAT('%', LOWER(:#{#request.name}), '%'))
        AND (:#{#request.priceFrom} IS NULL OR p.price >= :#{#request.priceFrom})
        AND (:#{#request.priceTo} IS NULL OR p.price <= :#{#request.priceTo})
        AND (:#{#request.targets}  IS NULL OR p.target IN :#{#request.targets})
    """)
    Page<ProductProjection> findAllProductProjections(FindProductRequest request, Pageable pageable);

    @Query("""
        SELECT
            p
        FROM Product p
        WHERE (:#{#request.name} IS NULL OR LOWER(p.name) LIKE CONCAT('%', LOWER(:#{#request.name}), '%'))
        AND (:#{#request.priceFrom} IS NULL OR p.price >= :#{#request.priceFrom})
        AND (:#{#request.priceTo} IS NULL OR p.price <= :#{#request.priceTo})
        AND (:#{#request.targets}  IS NULL OR p.target IN :#{#request.targets})
    """)
    List<Product> findAllProducts(FindProductRequest request);

    Optional<Product> findBySlug(String slug);
}
