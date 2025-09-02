package com.jimmyproject.accountservice.exceptions;

/**
 * Exception thrown when an account has insufficient funds for a withdrawal or transfer.
 */
public class InsufficientFundsException extends RuntimeException {
    
    public InsufficientFundsException(String message) {
        super(message);
    }
    
    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
}
