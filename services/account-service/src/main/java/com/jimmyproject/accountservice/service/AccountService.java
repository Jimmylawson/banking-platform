package com.jimmyproject.accountservice.service;

import com.jimmyproject.accountservice.dtos.AccountRequestDto;
import com.jimmyproject.accountservice.dtos.AccountResponseDto;
import com.jimmyproject.accountservice.enums.AccountStatus;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service interface for account operations.
 */
public interface AccountService {

    /**
     * Create a new account.
     *
     * @param requestDto the account data to create
     * @return the created account
     */
    AccountResponseDto createAccount(AccountRequestDto requestDto);

    /**
     * Get an account by ID.
     *
     * @param id the account ID
     * @return the account data
     * @throws com.jimmyproject.accountservice.exceptions.ResourceNotFoundException if account not found
     */
    AccountResponseDto getAccountById(Long id);

    /**
     * Get all accounts.
     *
     * @return list of all accounts
     */
    List<AccountResponseDto> getAllAccounts();

    /**
     * Update an account.
     *
     * @param id the account ID
     * @param requestDto the account data to update
     * @return the updated account
     * @throws com.jimmyproject.accountservice.exceptions.ResourceNotFoundException if account not found
     */
    AccountResponseDto updateAccount(Long id, AccountRequestDto requestDto);

    /**
     * Delete an account by ID.
     *
     * @param id the account ID to delete
     */
    void deleteAccount(Long id);

    /**
     * Update account status.
     *
     * @param id the account ID
     * @param status the new status
     * @return the updated account
     * @throws com.jimmyproject.accountservice.exceptions.ResourceNotFoundException if account not found
     */
    AccountResponseDto updateAccountStatus(Long id, AccountStatus status);

    /**
     * Get all accounts for a specific customer.
     *
     * @param customerId the customer ID
     * @return list of accounts for the customer
     */
    List<AccountResponseDto> getAccountsByCustomerId(Long customerId);

    /**
     * Update account balance.
     *
     * @param id the account ID
     * @param amount the amount to update (positive for deposit, negative for withdrawal)
     * @return the updated account
     * @throws com.jimmyproject.accountservice.exceptions.ResourceNotFoundException if account not found
     * @throws com.jimmyproject.accountservice.exceptions.InsufficientFundsException if insufficient funds for withdrawal
     */
    AccountResponseDto updateBalance(Long id, BigDecimal amount);

    /**
     * Transfer amount between accounts.
     *
     * @param fromAccountId the source account ID
     * @param toAccountId the target account ID
     * @param amount the amount to transfer
     * @throws com.jimmyproject.accountservice.exceptions.ResourceNotFoundException if any account not found
     * @throws com.jimmyproject.accountservice.exceptions.InsufficientFundsException if insufficient funds in source account
     */
    void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount);
}
