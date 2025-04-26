package com.inditex.apiprice.infrastructure.adapter.in.controller;

import com.inditex.apiprice.application.dto.response.PriceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static com.inditex.apiprice.infrastructure.util.MessageError.PRICE_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PriceControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @ParameterizedTest(name = "{index} => date={0}, productId={1}, brandId={2}, expectedPriceList={3}, expectedPrice={4}")
    @CsvSource({
            "2020-06-14T10:00:00, 35455, 1, 1, 35.50",
            "2020-06-14T16:00:00, 35455, 1, 2, 25.45",
            "2020-06-14T21:00:00, 35455, 1, 1, 35.50",
            "2020-06-15T10:00:00, 35455, 1, 3, 30.50",
            "2020-06-16T21:00:00, 35455, 1, 4, 38.95"
    })
    @DisplayName("Integration Test: Validate different price responses based on date, product and brand")
    void when_requestingPrice_then_expectedResult(String applicationDate, Long productId, Long brandId, Integer expectedPriceList, BigDecimal expectedPrice) {
        String url = String.format("/api/v1/prices?productId=%d&brandId=%d&applicationDate=%s", productId, brandId, applicationDate);

        ResponseEntity<PriceResponse> response = restTemplate.getForEntity(url, PriceResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPriceList()).isEqualTo(expectedPriceList);
        assertThat(response.getBody().getPrice()).isEqualByComparingTo(expectedPrice);
    }

    @Test
    @DisplayName("Integration Test: Should return 404 when no price is found")
    void when_noPriceFound_then_return404() {
        String url = "/api/v1/prices?productId=99999&brandId=1&applicationDate=2020-06-14T10:00:00";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains(PRICE_NOT_FOUND);
    }

    @Test
    @DisplayName("Integration Test: Should return 400 when missing parameters")
    void when_missingParameters_then_return400() {
        String url = "/api/v1/prices?productId=35455&brandId=1"; // falta applicationDate

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Required request parameter");
    }

    @Test
    @DisplayName("Integration Test: Should return 400 when parameter type is invalid")
    void when_invalidParameterType_then_return400() {
        String url = "/api/v1/prices?productId=invalid&brandId=1&applicationDate=2020-06-14T10:00:00";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("could not be converted to type");
    }
}
