package ru.app.spring.warehouseservice.api.dto.productDto;

import ru.app.spring.warehouseservice.domain.enums.ProductStatus;

import java.math.BigDecimal;

public record ResponseProductDto(
        Long id,
        String name,
        String  sku,
        BigDecimal price,
        ProductStatus status
) {
}
