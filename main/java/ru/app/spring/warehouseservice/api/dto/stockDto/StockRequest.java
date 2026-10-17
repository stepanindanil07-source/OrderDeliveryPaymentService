package ru.app.spring.warehouseservice.api.dto.stockDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record StockRequest(
        @Positive @NotBlank Integer quantity
) {
}
