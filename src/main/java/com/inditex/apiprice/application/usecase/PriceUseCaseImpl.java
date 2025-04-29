package com.inditex.apiprice.application.usecase;

import com.inditex.apiprice.application.mapper.PriceMapper;
import com.inditex.apiprice.domain.exception.PriceNotFoundException;
import com.inditex.apiprice.domain.port.in.PriceUseCase;
import com.inditex.apiprice.domain.port.out.PriceRepositoryPort;
import com.inditex.apiprice.infrastructure.dto.response.PriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.inditex.apiprice.infrastructure.util.MessageError.PRICE_NOT_FOUND;

@Slf4j
@Service
public class PriceUseCaseImpl implements PriceUseCase {

    private final PriceRepositoryPort repository;

    public PriceUseCaseImpl(PriceRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public PriceResponse findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        log.info("PriceUseCaseImpl::findApplicablePrice INIT productId={}, brandId={}, applicationDate={}",
                productId, brandId, applicationDate);

        return this.repository.findPrice(productId, brandId, applicationDate)
                .map(PriceMapper::toResponse)
                .orElseThrow(() -> {
                    log.warn("PriceUseCaseImpl::findApplicablePrice Price not found for productId={}, brandId={}, applicationDate={}", productId, brandId, applicationDate);
                    return new PriceNotFoundException(PRICE_NOT_FOUND);
                });
    }
}
