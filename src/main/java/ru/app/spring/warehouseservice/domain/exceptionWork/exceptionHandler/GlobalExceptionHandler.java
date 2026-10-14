package ru.app.spring.warehouseservice.domain.exceptionWork.exceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.app.spring.warehouseservice.domain.exceptionWork.customException.DuplicateSkuException;
import ru.app.spring.warehouseservice.domain.exceptionWork.dto.ErrorResponseDto;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(
            Exception e
    ) {
        log.error("Handle exeption", e);

        var errorDTO = new ErrorResponseDto(
                "Internal server error",
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorDTO);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(
            EntityNotFoundException e
    ) {
        log.error("Handle EntityNotFoundExeption", e);
        var errorDTO = new ErrorResponseDto(
                "Entity not found",
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorDTO);
    }

    @ExceptionHandler(exception = {
            IllegalStateException.class,
    })
    public ResponseEntity<ErrorResponseDto> handleBadRequest(
            IllegalStateException e
    ) {
        log.error("Handle BadRequest", e);
        var errorDTO = new ErrorResponseDto(
                "Bad request",
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDTO);
    }

    public ResponseEntity<ErrorResponseDto> handleDuplicateSkuException(
            DuplicateSkuException e
    ) {
        log.error("Handle DuplicateSkuException", e);
        var errorDTO = new ErrorResponseDto(
                "Duplicate sku",
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorDTO);
    }
}