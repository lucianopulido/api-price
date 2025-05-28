package com.inditex.apiprice.application.usecase;

import com.inditex.apiprice.domain.exception.PriceNotFoundException;
import com.inditex.apiprice.domain.model.Price;
import com.inditex.apiprice.domain.port.out.PriceRepositoryPort;
import com.inditex.apiprice.infrastructure.dto.response.PriceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.inditex.apiprice.infrastructure.util.MessageError.PRICE_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceUseCaseImplTest {

    @Mock
    private PriceRepositoryPort repository;

    @InjectMocks
    private PriceUseCaseImpl useCase;

    @Test
    @DisplayName("Should return applicable price with all fields validated")
    void shouldReturnPriceWithAllFieldsValidated() {
        Price expected = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .priceList(2)
                .priority(1)
                .price(BigDecimal.valueOf(25.45))
                .startDate(LocalDateTime.parse("2020-06-14T15:00:00"))
                .endDate(LocalDateTime.parse("2020-06-14T18:30:00"))
                .currency("EUR")
                .build();

        when(repository.findPrice(35455L, 1L, LocalDateTime.parse("2020-06-14T16:00:00")))
                .thenReturn(Optional.of(expected));

        PriceResponse response = useCase.findApplicablePrice(
                LocalDateTime.parse("2020-06-14T16:00:00"), 35455L, 1L
        );

        assertThat(response).isNotNull();
        assertThat(response.getBrandId()).isEqualTo(expected.getBrandId());
        assertThat(response.getProductId()).isEqualTo(expected.getProductId());
        assertThat(response.getPriceList()).isEqualTo(expected.getPriceList());
        assertThat(response.getPrice()).isEqualByComparingTo(expected.getPrice());
        assertThat(response.getStartDate()).isEqualTo(expected.getStartDate());
        assertThat(response.getEndDate()).isEqualTo(expected.getEndDate());
    }

    @Test
    @DisplayName("Should throw PriceNotFoundException when no prices are found")
    void shouldThrowExceptionWhenNoPrices() {
        LocalDateTime applicationDate = LocalDateTime.now();

        when(repository.findPrice(99999L, 1L, applicationDate))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.findApplicablePrice(applicationDate, 99999L, 1L))
                .isInstanceOf(PriceNotFoundException.class)
                .hasMessage(PRICE_NOT_FOUND);
    }
}
