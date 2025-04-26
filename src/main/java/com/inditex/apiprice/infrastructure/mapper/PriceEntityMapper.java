package com.inditex.apiprice.infrastructure.mapper;

import com.inditex.apiprice.domain.model.Price;
import com.inditex.apiprice.infrastructure.entity.PriceEntity;


public class PriceEntityMapper {

    private PriceEntityMapper() {
    }

    public static Price toDomain(PriceEntity entity) {
        return new Price(
                entity.getBrandId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPriceList(),
                entity.getProductId(),
                entity.getPriority(),
                entity.getPrice(),
                entity.getCurrency()
        );
    }
}
