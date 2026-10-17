package ru.app.spring.warehouseservice.domain.service;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.app.spring.warehouseservice.api.dto.productDto.RequestProductDto;
import ru.app.spring.warehouseservice.api.dto.productDto.ResponseProductDto;
import ru.app.spring.warehouseservice.api.dto.productDto.ProductSearchFilter;
import ru.app.spring.warehouseservice.domain.db.repository.ProductEntityRepository;
import ru.app.spring.warehouseservice.domain.db.repository.StockEntityRepository;
import ru.app.spring.warehouseservice.domain.db.entity.StockEntity;
import ru.app.spring.warehouseservice.domain.exceptionWork.customException.DuplicateSkuException;
import ru.app.spring.warehouseservice.domain.db.entity.ProductEntity;
import ru.app.spring.warehouseservice.domain.enums.ProductStatus;
import ru.app.spring.warehouseservice.domain.mapper.ProductMapper;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductEntityRepository productEntityRepository;
    private final ProductMapper productMapper;
    private final StockEntityRepository stockEntityRepository;

    @Transactional
    public ResponseProductDto createProduct(RequestProductDto request) {
        log.info("Creating product in processor, request: {}", request);

        var entity = productMapper.toEntity(request);
        entity.setSku(request.sku().toUpperCase());
        entity.setStatus(ProductStatus.DEACTIVATED);

        if (productEntityRepository.findBySku(entity.getSku())!=null) {
            throw new DuplicateSkuException("Duplication of SKU with another product, SKU = " + entity.getSku());
        }

        var savedEntity = productEntityRepository.save(entity);

        StockEntity stockEntity = new StockEntity();
        stockEntity.setProduct(savedEntity);
        stockEntity.setQuantity(0);

        stockEntityRepository.save(stockEntity);

        return productMapper.toDto(savedEntity);
    }

    public ResponseProductDto getProductById(Long id) {
        log.info("Getting product in processor, id: {}", id);
        var entity = getEntityFromDb(id);

        return productMapper.toDto(entity);
    }

    public List<ResponseProductDto> getAllByFilter(ProductSearchFilter filter) {
        log.info("Getting all products in processor");

        int pageSize = filter.pageSize() != null
                ? filter.pageSize()
                : 10;
        int pageNumber = filter.pageNumber() !=null
                ? filter.pageNumber()
                : 0;
        ProductStatus status = filter.status() !=null
                ? filter.status()
                : ProductStatus.ACTIVE;

        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNumber);

        var pageResult = productEntityRepository.searchAllByFilter(
                filter.name(),
                status,
                pageable);

        List<ProductEntity> allEntities = pageResult.getContent();

        return allEntities.stream().map(productMapper::toDto).toList();
    }

    @Transactional
    public ResponseProductDto activateProduct(Long id) {
        log.info("Activating product in processor, id: {}", id);
        var entity = getEntityFromDb(id);
        if (entity.getStatus().equals(ProductStatus.ACTIVE)) {
            throw new IllegalStateException("Product is already active.");
        }
        entity.setStatus(ProductStatus.ACTIVE);
        return productMapper.toDto(productEntityRepository.save(entity));
    }

    @Transactional
    public ResponseProductDto deactivateProduct(Long id) {
        log.info("Deactivating product in processor, id: {}", id);
        var entity = getEntityFromDb(id);
        if (entity.getStatus().equals(ProductStatus.DEACTIVATED)) {
            throw new IllegalStateException("Product is already deactivated.");
        }
        entity.setStatus(ProductStatus.DEACTIVATED);
        return productMapper.toDto(productEntityRepository.save(entity));
    }

    @Transactional
    public ResponseProductDto updateName(Long id, String name) {
        log.info("Updating product`s name in processor, id: {}", id);
        var entity = getEntityFromDb(id);
        if(entity.getName().equals(name)){
            throw new IllegalStateException("Product name is already in use.");
        }
        entity.setName(name);
        return productMapper.toDto(productEntityRepository.save(entity));
    }

    @Transactional
    public ResponseProductDto updatePrice(Long id, BigDecimal price) {
        log.info("Updating product`s price in processor, id: {}", id);
        var entity = getEntityFromDb(id);
        if (entity.getPrice().compareTo(price)==0){
            throw new IllegalStateException("Product price is already in use.");
        }
        entity.setPrice(price);
        return productMapper.toDto(productEntityRepository.save(entity));
    }






    private ProductEntity getEntityFromDb(Long id) {
        log.info("Getting product entity from db, id: {}", id);
        var entity = productEntityRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Product not found with id: " + id));
        return entity;
    }

}
