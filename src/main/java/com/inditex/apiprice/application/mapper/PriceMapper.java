package com.inditex.apiprice.application.mapper;

import com.inditex.apiprice.infrastructure.dto.response.PriceResponse;
import com.inditex.apiprice.domain.model.Price;

public final class PriceMapper {

    private PriceMapper() {
    }

    public static PriceResponse toResponse(Price price) {
        return new PriceResponse(
                price.getProductId(),
                price.getBrandId(),
                price.getPriceList(),
                price.getStartDate(),
                price.getEndDate(),
                price.getPrice()
        );
    }
}
