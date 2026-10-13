package ru.app.spring.warehouseservice.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.app.spring.warehouseservice.domain.entity.ProductEntity;

public interface ProductEntityRepository extends JpaRepository<ProductEntity, Long> {
}