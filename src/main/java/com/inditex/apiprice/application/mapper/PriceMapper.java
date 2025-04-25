package com.inditex.apiprice.application.mapper;

import com.inditex.apiprice.application.dto.response.PriceResponse;
import com.inditex.apiprice.domain.model.Price;
import org.springframework.stereotype.Component;

@Component
public class PriceMapper {
    public PriceResponse toResponse(Price price) {
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
