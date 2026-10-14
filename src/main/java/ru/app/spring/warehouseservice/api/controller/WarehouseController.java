package ru.app.spring.warehouseservice.api.controller;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.app.spring.warehouseservice.api.dto.CreateRequestProductDto;
import ru.app.spring.warehouseservice.api.dto.CreateResponseProductDto;
import ru.app.spring.warehouseservice.api.dto.ProductSearchFilter;
import ru.app.spring.warehouseservice.domain.enums.ProductStatus;
import ru.app.spring.warehouseservice.domain.processor.WarehouseProcessor;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/warehouse")
@Slf4j
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseProcessor warehouseProcessor;

    @PostMapping("/products")
    public ResponseEntity<CreateResponseProductDto> create(
            @RequestBody CreateRequestProductDto request
    ) {
        log.info("called create api method with request {}", request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(warehouseProcessor.createProduct(request));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<CreateResponseProductDto> get(
            @PathVariable Long id
    ) {
        log.info("called get api method with id {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(warehouseProcessor.getProductById(id));
    }

    @GetMapping("/products")
    public ResponseEntity<List<CreateResponseProductDto>> getAllByFilter(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "status", required = false) ProductStatus status,
            @RequestParam(name = "pageSize", required = false) int pageSize,
            @RequestParam(name = "pageNumber",required = false) int pageCount
    ) {
        log.info("called get all api method");
        var filter = new ProductSearchFilter(
                name,
                status,
                pageSize,
                pageCount
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(warehouseProcessor.getAllByFilter(filter));
    }

    @PatchMapping("/products/{id}/activate")
    public ResponseEntity<CreateResponseProductDto> activate(
            @PathVariable Long id
    ){
        log.info("called activate api method with id {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(warehouseProcessor.activateProduct(id));
    }

    @PatchMapping("/products/{id}/deactivate")
    public ResponseEntity<CreateResponseProductDto> deactivate(
            @PathVariable Long id
    ){
        log.info("called deactivate api method with id {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(warehouseProcessor.deactivateProduct(id));
    }

    @PatchMapping("/products/{id}/name")
    public ResponseEntity<CreateResponseProductDto> updateName(
            @PathVariable Long id,
            @RequestParam String name
    ){
        log.info("called update api method with id {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(warehouseProcessor.updateName(id,name));
    }

    @PatchMapping("/products/{id}/price")
    public ResponseEntity<CreateResponseProductDto> updatePrice(
            @PathVariable Long id,
            @RequestParam
            @DecimalMin(value = "0",inclusive = false)
            @Digits(integer = 11, fraction = 2) BigDecimal price
    ){
        log.info("called update api method with id {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(warehouseProcessor.updatePrice(id,price));
    }

}
