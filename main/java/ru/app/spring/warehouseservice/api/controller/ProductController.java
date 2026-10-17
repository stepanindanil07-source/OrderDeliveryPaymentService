package ru.app.spring.warehouseservice.api.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.app.spring.warehouseservice.api.dto.productDto.RequestProductDto;
import ru.app.spring.warehouseservice.api.dto.productDto.ResponseProductDto;
import ru.app.spring.warehouseservice.api.dto.productDto.ProductSearchFilter;
import ru.app.spring.warehouseservice.domain.enums.ProductStatus;
import ru.app.spring.warehouseservice.domain.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/warehouse/products")
@Slf4j
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService warehouseProcessor;

    @PostMapping()
    public ResponseEntity<ResponseProductDto> create(
            @Valid @RequestBody RequestProductDto request
    ) {
        log.info("called create product endpoint with request {}", request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(warehouseProcessor.createProduct(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseProductDto> get(
            @PathVariable Long id
    ) {
        log.info("called get product endpoint with id {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(warehouseProcessor.getProductById(id));
    }

    @GetMapping()
    public ResponseEntity<List<ResponseProductDto>> getAllByFilter(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "status", required = false) ProductStatus status,
            @Positive @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @Positive @Max(25) @RequestParam(name = "pageNumber",required = false) Integer pageCount
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

    @PatchMapping("/{id}/activate")
    public ResponseEntity<ResponseProductDto> activate(
            @PathVariable Long id
    ){
        log.info("called activate product endpoint with id {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(warehouseProcessor.activateProduct(id));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ResponseProductDto> deactivate(
            @PathVariable Long id
    ){
        log.info("called deactivate product endpoint with id {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(warehouseProcessor.deactivateProduct(id));
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<ResponseProductDto> updateName(
            @PathVariable Long id,
            @RequestParam String name
    ){
        log.info("called update product endpoint with id {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(warehouseProcessor.updateName(id,name));
    }

    @PatchMapping("/{id}/price")
    public ResponseEntity<ResponseProductDto> updatePrice(
            @PathVariable Long id,
            @RequestParam
            @DecimalMin(value = "0",inclusive = false)
            @Digits(integer = 11, fraction = 2) BigDecimal price
    ){
        log.info("called update product endpoint with id {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(warehouseProcessor.updatePrice(id,price));
    }

}
