package com.inditex.apiprice.infrastructure.adapter.out.repository;

import com.inditex.apiprice.infrastructure.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {

    @Query(value = "SELECT * FROM PRICES p " +
            "WHERE p.product_id = :productId " +
            "AND p.brand_id = :brandId " +
            "AND :applicationDate BETWEEN p.start_date AND p.end_date " +
            "ORDER BY p.priority DESC " +
            "LIMIT 1",
            nativeQuery = true)
    PriceEntity findTopPrice(@Param("productId") Long productId,
                             @Param("brandId") Long brandId,
                             @Param("applicationDate") LocalDateTime applicationDate);
}
