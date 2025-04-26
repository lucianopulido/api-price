package com.inditex.apiprice.application.usecase;

import com.inditex.apiprice.application.dto.response.PriceResponse;
import com.inditex.apiprice.application.mapper.PriceMapper;
import com.inditex.apiprice.domain.model.Price;
import com.inditex.apiprice.domain.port.in.PriceUseCase;
import com.inditex.apiprice.domain.port.out.PriceRepositoryPort;
import com.inditex.apiprice.infrastructure.exception.PriceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;

import static com.inditex.apiprice.infrastructure.util.MessageError.PRICE_NOT_FOUND;

@Service
public class PriceUseCaseImpl implements PriceUseCase {

    private final PriceRepositoryPort repository;
    private final PriceMapper mapper;

    public PriceUseCaseImpl(PriceRepositoryPort repository, PriceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PriceResponse findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return repository.findPrices(productId, brandId, applicationDate).stream()
                .max(Comparator.comparingInt(Price::getPriority))
                .map(mapper::toResponse)
                .orElseThrow(() -> new PriceNotFoundException(PRICE_NOT_FOUND));
    }
}
