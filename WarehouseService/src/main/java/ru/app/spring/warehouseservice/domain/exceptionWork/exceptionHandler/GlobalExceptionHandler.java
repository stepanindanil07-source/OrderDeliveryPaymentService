package ru.app.spring.warehouseservice.domain.exceptionWork.exceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.app.spring.warehouseservice.domain.exceptionWork.customException.AddStockForDeactivatedProductException;
import ru.app.spring.warehouseservice.domain.exceptionWork.customException.DuplicateSkuException;
import ru.app.spring.warehouseservice.domain.exceptionWork.dto.ErrorResponseDto;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(
            EntityNotFoundException e
    ) {
        log.error("Handle EntityNotFoundExeption",e, e.getMessage());
        var errorDTO = new ErrorResponseDto(
                "Entity not found",
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorDTO);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponseDto> handleBadRequest(
            IllegalStateException e
    ) {
        log.error("Handle BadRequest", e, e.getMessage());
        var errorDTO = new ErrorResponseDto(
                "Already yet",
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorDTO);
    }

    @ExceptionHandler(exception = {
            DuplicateSkuException.class,
            AddStockForDeactivatedProductException.class
    })
    public ResponseEntity<ErrorResponseDto> handleDuplicateSkuException(
            Exception e
    ) {
        log.error("Handle DuplicateSkuException", e, e.getMessage());
        var errorDTO = new ErrorResponseDto(
                "Duplicate sku",
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorDTO);
    }

    @ExceptionHandler(exception = {
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class,
            DataIntegrityViolationException.class
    })
    public ResponseEntity<ErrorResponseDto> handleSpringException(
            Exception e
    ){
        log.error("Handle SpringException", e, e.getMessage());
        var errorDTO = new ErrorResponseDto(
                "Not valid Date",
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDTO);
    }

    @ExceptionHandler(exception = {
            UnexpectedTypeException.class
    })
    public ResponseEntity<ErrorResponseDto> handleValidationException(
            Exception e
    ){
        log.error("Handle ValidationException", e, e.getMessage());
        var errorDTO = new ErrorResponseDto(
                "Validation Exception",
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(
            Exception e
    ) {
        log.error("Handle exeption", e, e.getMessage());

        var errorDTO = new ErrorResponseDto(
                "Internal server error",
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorDTO);
    }
}