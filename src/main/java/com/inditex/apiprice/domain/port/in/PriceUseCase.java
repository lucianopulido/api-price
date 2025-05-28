package com.inditex.apiprice.domain.port.in;


import com.inditex.apiprice.domain.model.Price;

import java.time.LocalDateTime;

public interface PriceUseCase {
    Price findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);
}
