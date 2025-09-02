package com.jimmyproject.accountservice.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String account, Long id) {
        super(String.format("%s not found with id %s", account, id));
    }
}
