package ru.app.spring.warehouseservice.domain.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.app.spring.warehouseservice.api.dto.stockDto.StockRequest;
import ru.app.spring.warehouseservice.api.dto.stockDto.StockResponse;
import ru.app.spring.warehouseservice.domain.db.repository.ProductEntityRepository;
import ru.app.spring.warehouseservice.domain.db.repository.StockEntityRepository;
import ru.app.spring.warehouseservice.domain.enums.ProductStatus;
import ru.app.spring.warehouseservice.domain.exceptionWork.customException.AddStockForDeactivatedProductException;
import ru.app.spring.warehouseservice.domain.mapper.ProductMapper;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockService {

    private final StockEntityRepository stockEntityRepository;
    private final ProductEntityRepository productEntityRepository;
    private final ProductMapper mapper;

    @Transactional
    public StockResponse addStock(Long id, StockRequest stockRequest) {
        log.info("adding stock. id={} request={}", id, stockRequest);
        var entity = productEntityRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Product not found with id: " + id));

        if (entity.getStatus().equals(ProductStatus.DEACTIVATED)){
            throw new AddStockForDeactivatedProductException("Product is deactivated and can't be added stock");
        }

        stockEntityRepository.addQuantity(id,stockRequest.quantity());

        var stockEntity = stockEntityRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Product not found with id: " + id)
        );

        return new StockResponse(mapper.toDto(entity),stockEntity.getQuantity());

    }


    public StockResponse getStock(Long id) {
        log.info("getting stock. id={}", id);
        var entity = productEntityRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Product not found with id: " + id));

        var stockEntity = stockEntityRepository.findById(id);

        if(stockEntity.isEmpty()){
            return new StockResponse(mapper.toDto(entity),0);
        }

        return new StockResponse(mapper.toDto(entity),stockEntity.get().getQuantity());
    }
}
