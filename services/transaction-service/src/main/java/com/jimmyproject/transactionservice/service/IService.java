package com.jimmyproject.transactionservice.service;

import com.jimmyproject.transactionservice.dtos.TransactionRequestDto;
import com.jimmyproject.transactionservice.dtos.TransactionResponseDto;
import com.jimmyproject.transactionservice.enums.TransactionStatus;

import java.util.List;

/**
 * Service interface for transaction operations.
 */
public interface IService {

    /**
     * Create a new transaction
     * @param transactionRequestDto The transaction details
     * @return The created transaction
     */
    TransactionResponseDto createTransaction(TransactionRequestDto transactionRequestDto);

    /**
     * Get a transaction by ID
     * @param id The transaction ID
     * @return The transaction details
     */
    TransactionResponseDto getTransactionById(Long id);

    /**
     * Get all transactions
     * @return List of all transactions
     */
    List<TransactionResponseDto> getAllTransactions();

    /**
     * Get transactions by account ID
     * @param accountId The account ID
     * @return List of transactions for the account
     */
    List<TransactionResponseDto> getTransactionsByAccountId(Long accountId);

    /**
     * Update transaction status
     * @param transactionId The transaction ID
     * @param status The new status
     * @return The updated transaction
     */
    TransactionResponseDto updateTransactionStatus(Long transactionId, TransactionStatus status);

    /**
     * Process a transaction
     * @param transactionRequestDto The transaction details
     * @return The processed transaction
     */
    TransactionResponseDto processTransaction(TransactionRequestDto transactionRequestDto);


    /**
     * Get transactions by status
     * @param status The transaction status
     * @return List of transactions with the given status
     */
    List<TransactionResponseDto> getTransactionsByStatus(TransactionStatus status);

    /**
     * Check if a transaction exists
     * @param transactionId The transaction ID
     * @return true if exists, false otherwise
     */
    boolean transactionExists(Long transactionId);
}
