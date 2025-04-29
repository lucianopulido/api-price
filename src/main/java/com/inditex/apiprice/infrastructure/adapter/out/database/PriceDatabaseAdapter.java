package com.inditex.apiprice.infrastructure.adapter.out.database;

import com.inditex.apiprice.domain.model.Price;
import com.inditex.apiprice.domain.port.out.PriceRepositoryPort;
import com.inditex.apiprice.infrastructure.adapter.out.repository.PriceJpaRepository;
import com.inditex.apiprice.infrastructure.mapper.PriceEntityMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class PriceDatabaseAdapter implements PriceRepositoryPort {

    private final PriceJpaRepository priceJpaRepository;

    public PriceDatabaseAdapter(PriceJpaRepository priceJpaRepository) {
        this.priceJpaRepository = priceJpaRepository;
    }

    @Override
    public Optional<Price> findPrice(Long productId, Long brandId, LocalDateTime applicationDate) {
        return Optional.ofNullable(
                this.priceJpaRepository.findTopPrice(productId, brandId, applicationDate)
        ).map(PriceEntityMapper::toDomain);
    }
}
