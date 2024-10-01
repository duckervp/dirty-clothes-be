package com.dirty.shop.repository;

import com.dirty.shop.dto.request.FindUserRequest;
import com.dirty.shop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("""
            SELECT u FROM User u
            WHERE (:#{#request.email} IS NULL OR u.email LIKE CONCAT('%',:#{#request.email},'%'))
            AND (:#{#request.name} IS NULL OR u.name LIKE CONCAT('%',:#{#request.name},'%'))
            AND (:#{#request.role} IS NULL OR u.role = :#{#request.role})
            AND (:#{#request.status} IS NULL OR u.status = :#{#request.status})
            """)
    Page<User> findUser(FindUserRequest request, Pageable pageable);
}
