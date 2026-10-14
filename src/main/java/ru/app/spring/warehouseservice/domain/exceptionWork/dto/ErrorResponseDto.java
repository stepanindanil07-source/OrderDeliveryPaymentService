package ru.app.spring.warehouseservice.domain.exceptionWork.dto;

import java.time.LocalDateTime;

public record ErrorResponseDto(
        String errorName,
        String errorDescription,
        LocalDateTime time
){
}
