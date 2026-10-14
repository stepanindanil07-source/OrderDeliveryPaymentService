package ru.app.spring.warehouseservice.domain.db.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.app.spring.warehouseservice.domain.entity.ProductEntity;
import ru.app.spring.warehouseservice.domain.enums.ProductStatus;

import java.util.List;

public interface ProductEntityRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity findBySku(String sku);

    @Query("""
       SELECT p FROM ProductEntity p
       WHERE (:name IS NULL OR p.name = :name)
       AND (:status IS NULL OR p.status = :status)
    """)
    Page<ProductEntity> searchAllByFilter(
            @Param("name") String name,
            @Param("status") ProductStatus status,
            Pageable pageable
    );


}

