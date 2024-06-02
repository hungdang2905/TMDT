package com.example.demo.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundExceptionHandler extends RuntimeException {

    private final String field;
    private final String value;

    public ResourceNotFoundExceptionHandler(String field, String value) {
        super(String.format("Không tìm thấy %s '%s' trong hệ thống.", field, value));
        this.field = field;
        this.value = value;
    }

}
