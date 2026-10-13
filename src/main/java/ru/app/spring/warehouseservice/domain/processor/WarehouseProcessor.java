package ru.app.spring.warehouseservice.domain.processor;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import ru.app.spring.warehouseservice.api.dto.CreateRequestProductDto;
import ru.app.spring.warehouseservice.api.dto.CreateResponseProductDto;
import ru.app.spring.warehouseservice.db.repository.ProductEntityRepository;
import ru.app.spring.warehouseservice.domain.entity.ProductEntity;
import ru.app.spring.warehouseservice.domain.enums.ProductStatus;
import ru.app.spring.warehouseservice.domain.mapper.ProductMapper;

import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class WarehouseProcessor {

    private final ProductEntityRepository productEntityRepository;
    private final ProductMapper productMapper;

    public CreateResponseProductDto createProduct(CreateRequestProductDto request) {
        ProductEntity product = productMapper.toEntity(request);
        product.setStatus(ProductStatus.DEACTIVATED);

        return productMapper.toDto(productEntityRepository.save(product));
    }

    public @Nullable CreateResponseProductDto getProductById(Long id) {
        var entity = productEntityRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Product not found with id: " + id));

        return productMapper.toDto(entity);
    }
}
