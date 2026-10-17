package ru.app.spring.warehouseservice.domain.db.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.app.spring.warehouseservice.domain.db.entity.StockEntity;

public interface StockEntityRepository extends JpaRepository<StockEntity, Long> {

    @Modifying()
    @Query("""
        UPDATE StockEntity s 
        SET s.quantity = s.quantity + :quantity
        WHERE s.id =:id 
    """)
    void addQuantity(
            @Param("id") Long id,
            @Param("quantity") Integer quantity
    );

}
