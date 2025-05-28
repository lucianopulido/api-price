package com.inditex.apiprice.domain.port.out;

import com.inditex.apiprice.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepositoryPort {
    Optional<Price> findPrice(Long productId, Long brandId, LocalDateTime applicationDate);
}
