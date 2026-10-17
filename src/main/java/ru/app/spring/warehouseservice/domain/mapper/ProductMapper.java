package ru.app.spring.warehouseservice.domain.mapper;

import org.springframework.stereotype.Component;
import ru.app.spring.warehouseservice.api.dto.productDto.RequestProductDto;
import ru.app.spring.warehouseservice.api.dto.productDto.ResponseProductDto;
import ru.app.spring.warehouseservice.domain.db.entity.ProductEntity;

@Component
public class ProductMapper {

    public ProductEntity toEntity(RequestProductDto requestProductDto) {
        var productEntity = ProductEntity.builder()
                .name(requestProductDto.name())
                .price(requestProductDto.price())
                .sku(requestProductDto.sku())
                .build();
        return productEntity;
    }

    public ResponseProductDto toDto(ProductEntity productEntity) {
        var responseProductDto = new ResponseProductDto(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getSku(),
                productEntity.getPrice(),
                productEntity.getStatus()
        );
        return responseProductDto;
    }
}
