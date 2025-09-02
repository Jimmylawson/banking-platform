package com.jimmyproject.customerservice.exceptions;

public class CustomerDuplicateException extends RuntimeException{

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public CustomerDuplicateException(String message) {
        super(message);
    }
    public CustomerDuplicateException(Long customerId){
        super("User with ID " + customerId + " already exists!");
    }
}
