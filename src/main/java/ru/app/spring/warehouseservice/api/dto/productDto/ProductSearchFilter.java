package ru.app.spring.warehouseservice.api.dto.productDto;

import ru.app.spring.warehouseservice.domain.enums.ProductStatus;

public record ProductSearchFilter(
        String name,
        ProductStatus status,
        Integer pageSize,
        Integer pageNumber
) {
}
