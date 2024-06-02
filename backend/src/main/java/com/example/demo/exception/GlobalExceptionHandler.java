package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Bắt validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionResponseHandler> handleValidationExceptions(MethodArgumentNotValidException exception, WebRequest webRequest) {

        ApiExceptionResponseHandler response = new ApiExceptionResponseHandler(
                HttpStatus.BAD_REQUEST.value(),
                "Validation thất bại.",
                exception.getBindingResult().getFieldError().getDefaultMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);

    }

    // Bắt trường dữ liệu đã tồn tại
    @ExceptionHandler(ResourceAlreadyExistsExceptionHandler.class)
    public ResponseEntity<ApiExceptionResponseHandler> handleResourceAlreadyExists(ResourceAlreadyExistsExceptionHandler ex) {

        ApiExceptionResponseHandler response = new ApiExceptionResponseHandler(
                HttpStatus.CONFLICT.value(),
                "Trường dữ liệu đã tồn tại.",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);

    }

}
