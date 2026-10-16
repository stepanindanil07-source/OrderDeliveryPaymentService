package ru.app.spring.warehouseservice.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.app.spring.warehouseservice.api.dto.stockDto.StockRequest;
import ru.app.spring.warehouseservice.api.dto.stockDto.StockResponse;
import ru.app.spring.warehouseservice.domain.processor.StockProcessor;

@RestController
@RequestMapping("/warehouse/stocks")
@Slf4j
@RequiredArgsConstructor
public class StockController {

    StockProcessor stockProcessor;

    @PostMapping("/{id}")
    public ResponseEntity<StockResponse> addStock(
            @PathVariable("id") Long id,
            @RequestBody StockRequest stockRequest)
    {
        log.info("called add stock endpoint with id={} and request={}", id, stockRequest );
        return ResponseEntity.status(HttpStatus.OK)
                .body(stockProcessor.addStock(id,stockRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockResponse> getStock(
            @PathVariable("id") Long id
    ){
        log.info("called get stock endpoint with id={}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(stockProcessor.getStock(id));
    }

}
