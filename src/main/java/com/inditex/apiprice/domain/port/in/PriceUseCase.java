package com.inditex.apiprice.domain.port.in;


import com.inditex.apiprice.application.dto.response.PriceResponse;

import java.time.LocalDateTime;

public interface PriceUseCase {
    PriceResponse findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);
}
