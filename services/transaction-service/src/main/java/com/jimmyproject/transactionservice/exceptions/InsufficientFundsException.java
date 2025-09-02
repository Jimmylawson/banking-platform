package com.jimmyproject.transactionservice.exceptions;


import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }

    public InsufficientFundsException(Long accountId, BigDecimal amount) {
        super(String.format("Insufficient funds in account %s for amount %s", accountId, amount));
    }
}
