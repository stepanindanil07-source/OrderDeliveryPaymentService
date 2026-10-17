package ru.app.spring.warehouseservice.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.app.spring.warehouseservice.api.dto.stockDto.StockRequest;
import ru.app.spring.warehouseservice.api.dto.stockDto.StockResponse;
import ru.app.spring.warehouseservice.domain.service.StockService;

@RestController
@RequestMapping("/warehouse/stocks")
@Slf4j
@RequiredArgsConstructor
@Validated
public class StockController {

    private final StockService stockService;

    @PostMapping("/{id}")
    public ResponseEntity<StockResponse> addStock(
            @PathVariable("id") Long id,
            @Valid @RequestBody StockRequest stockRequest)
    {
        log.info("called add stock endpoint with id={} and request={}", id, stockRequest );
        return ResponseEntity.status(HttpStatus.OK)
                .body(stockService.addStock(id,stockRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockResponse> getStock(
            @PathVariable("id") Long id
    ){
        log.info("called get stock endpoint with id={}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(stockService.getStock(id));
    }

}
