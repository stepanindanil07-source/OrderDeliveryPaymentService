package ru.app.spring.warehouseservice.domain.db.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.app.spring.warehouseservice.domain.enums.ProductStatus;

import java.math.BigDecimal;

@Entity
@Table(name = "warehouse_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sku", unique = true)
    private String sku;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private StockEntity quantityStock;


}
