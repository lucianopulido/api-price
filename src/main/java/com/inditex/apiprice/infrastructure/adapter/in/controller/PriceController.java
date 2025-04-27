package com.inditex.apiprice.infrastructure.adapter.in.controller;

import com.inditex.apiprice.application.dto.response.PriceResponse;
import com.inditex.apiprice.domain.port.in.PriceUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/prices")
@Tag(name = "Prices API", description = "Operations related to product prices")
public class PriceController {

    private final PriceUseCase priceUseCase;

    public PriceController(PriceUseCase priceUseCase) {
        this.priceUseCase = priceUseCase;
    }

    @GetMapping
    @Operation(
            summary = "Get applicable price",
            description = "Returns the applicable price for a product, brand, and application date."
    )
    public ResponseEntity<PriceResponse> getPrice(
            @Parameter(description = "Application Date in ISO-8601 format", example = "2020-06-14T16:00:00", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
            @Parameter(description = "Product ID", example = "35455", required = true)
            @RequestParam Long productId,
            @Parameter(description = "Brand ID", example = "1", required = true)
            @RequestParam Long brandId
    ) {
        return ResponseEntity.ok(priceUseCase.findApplicablePrice(applicationDate, productId, brandId));
    }
}
