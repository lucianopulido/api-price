package com.inditex.apiprice.domain.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Price {

    private final Long brandId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Integer priceList;
    private final Long productId;
    private final Integer priority;
    private final BigDecimal price;
    private final String currency;
}
