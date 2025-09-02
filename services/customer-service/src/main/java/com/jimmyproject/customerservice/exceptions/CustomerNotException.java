package com.jimmyproject.customerservice.exceptions;


public class CustomerNotException extends RuntimeException{
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public CustomerNotException(String message) {
        super(message);
    }
    public CustomerNotException(Long customerId){
        super("User with ID " + customerId + " not found!");
    }
}
