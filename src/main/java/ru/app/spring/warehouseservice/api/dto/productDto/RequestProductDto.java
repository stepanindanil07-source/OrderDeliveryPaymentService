package ru.app.spring.warehouseservice.api.dto.productDto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RequestProductDto(
        @NotNull
        String  name,
        @NotNull
        String  sku,
        @DecimalMin(value = "0",inclusive = false)
        @Digits(integer = 11, fraction = 2)
        @NotNull
        BigDecimal price
) {
}
