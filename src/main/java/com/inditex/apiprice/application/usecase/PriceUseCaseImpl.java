package com.inditex.apiprice.application.usecase;

import com.inditex.apiprice.application.dto.response.PriceResponse;
import com.inditex.apiprice.application.mapper.PriceMapper;
import com.inditex.apiprice.domain.model.Price;
import com.inditex.apiprice.domain.port.in.PriceUseCase;
import com.inditex.apiprice.domain.port.out.PriceRepositoryPort;
import com.inditex.apiprice.infrastructure.exception.PriceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;

import static com.inditex.apiprice.infrastructure.util.MessageError.PRICE_NOT_FOUND;

@Slf4j
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
        log.info("PriceUseCaseImpl::findApplicablePrice INIT productId={}, brandId={}, applicationDate={}", productId, brandId, applicationDate);

        return repository.findPrices(productId, brandId, applicationDate).stream()
                .max(Comparator.comparingInt(Price::getPriority))
                .map(mapper::toResponse)
                .orElseThrow(() -> {
                    log.warn("PriceUseCaseImpl::findApplicablePrice Price not found for productId={}, brandId={}, applicationDate={}", productId, brandId, applicationDate);
                    return new PriceNotFoundException(PRICE_NOT_FOUND);
                });
    }
}
