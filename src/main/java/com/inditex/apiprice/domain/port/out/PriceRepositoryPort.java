package com.inditex.apiprice.domain.port.out;

import com.inditex.apiprice.domain.model.Price;
import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepositoryPort {
    List<Price> findPrices(Long productId, Long brandId, LocalDateTime applicationDate);
}
