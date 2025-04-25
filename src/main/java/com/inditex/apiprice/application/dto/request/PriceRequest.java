package com.inditex.apiprice.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PriceRequest {
    private final LocalDateTime applicationDate;
    private final Long productId;
    private final Long brandId;
}
