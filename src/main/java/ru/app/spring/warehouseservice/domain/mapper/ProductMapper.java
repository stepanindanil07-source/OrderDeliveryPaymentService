package ru.app.spring.warehouseservice.domain.mapper;

import org.springframework.stereotype.Component;
import ru.app.spring.warehouseservice.api.dto.CreateRequestProductDto;
import ru.app.spring.warehouseservice.api.dto.CreateResponseProductDto;
import ru.app.spring.warehouseservice.domain.entity.ProductEntity;

@Component
public class ProductMapper {

    public ProductEntity toEntity(CreateRequestProductDto requestProductDto) {
        var productEntity = ProductEntity.builder()
                .name(requestProductDto.name())
                .price(requestProductDto.price())
                .sku(requestProductDto.sku())
                .build();
        return productEntity;
    }

    public CreateResponseProductDto toDto(ProductEntity productEntity) {
        var responseProductDto = new CreateResponseProductDto(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getSku(),
                productEntity.getPrice(),
                productEntity.getStatus()
        );
        return responseProductDto;
    }
}
