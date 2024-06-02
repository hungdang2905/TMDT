package com.example.demo.exception;

import lombok.Getter;

@Getter
public class ResourceAlreadyExistsExceptionHandler extends RuntimeException {
    private final String resource;
    private final String field;
    private final String value;

    public ResourceAlreadyExistsExceptionHandler(String resource, String field, String value) {
        super(String.format("%s đã tồn tại với %s: '%s'", resource, field, value));
        this.resource = resource;
        this.field = field;
        this.value = value;
    }

}
