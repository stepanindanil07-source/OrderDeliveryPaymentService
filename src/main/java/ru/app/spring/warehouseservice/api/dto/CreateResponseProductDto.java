package ru.app.spring.warehouseservice.api.dto;

import ru.app.spring.warehouseservice.domain.enums.ProductStatus;

import java.math.BigDecimal;

public record CreateResponseProductDto(
        Long id,
        String name,
        String  sku,
        BigDecimal price,
        ProductStatus status
) {
}
