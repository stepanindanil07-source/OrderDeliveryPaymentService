package ru.app.spring.warehouseservice.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.app.spring.warehouseservice.api.dto.CreateRequestProductDto;
import ru.app.spring.warehouseservice.api.dto.CreateResponseProductDto;
import ru.app.spring.warehouseservice.domain.entity.ProductEntity;
import ru.app.spring.warehouseservice.domain.processor.WarehouseProcessor;

@RestController
@RequestMapping("/warehouse")
@Slf4j
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseProcessor warehouseProcessor;

    @PostMapping("/create/product")
    public ResponseEntity<CreateResponseProductDto> create(
            @RequestBody CreateRequestProductDto request
    ) {
        log.info("called create api method with request {}", request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(warehouseProcessor.createProduct(request));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CreateResponseProductDto> get(
            @PathVariable Long id
    ) {
        log.info("called get api method with id {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(warehouseProcessor.getProductById(id));
    }
}
