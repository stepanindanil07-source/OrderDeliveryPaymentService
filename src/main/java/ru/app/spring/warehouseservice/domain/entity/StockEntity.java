package ru.app.spring.warehouseservice.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products_stocks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @PositiveOrZero
    @Column(name = "quantity")
    private Integer quantity;

    @OneToOne(mappedBy = "quantityStock")
    private ProductEntity product;
}
