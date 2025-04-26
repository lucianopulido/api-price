package com.inditex.apiprice.infrastructure.adapter.out.repository;

import com.inditex.apiprice.infrastructure.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {

    List<PriceEntity> findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long productId,
            Long brandId,
            LocalDateTime applicationDateStart,
            LocalDateTime applicationDateEnd
    );
}
