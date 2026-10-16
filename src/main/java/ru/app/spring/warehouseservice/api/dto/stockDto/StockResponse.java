package ru.app.spring.warehouseservice.api.dto.stockDto;

import ru.app.spring.warehouseservice.api.dto.productDto.ResponseProductDto;
import ru.app.spring.warehouseservice.domain.entity.ProductEntity;

public record StockResponse(
        ResponseProductDto productDto,
        Integer quantity
) {
}
