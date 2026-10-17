package ru.app.spring.warehouseservice.domain.exceptionWork.customException;

public class AddStockForDeactivatedProductException extends RuntimeException {
    public AddStockForDeactivatedProductException(String message) {
        super(message);
    }
}
