package ru.app.spring.warehouseservice.domain.exceptionWork.customException;

public class DuplicateSkuException extends RuntimeException {
    public DuplicateSkuException(String message) {
        super(message);
    }
}
