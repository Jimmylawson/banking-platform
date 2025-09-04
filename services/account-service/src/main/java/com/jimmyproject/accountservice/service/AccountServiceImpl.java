package com.jimmyproject.accountservice.service;

import com.jimmyproject.accountservice.dtos.AccountCustomerResponse;
import com.jimmyproject.accountservice.dtos.AccountRequestDto;
import com.jimmyproject.accountservice.dtos.AccountResponseDto;
import com.jimmyproject.accountservice.dtos.CustomerResponseDto;
import com.jimmyproject.accountservice.entity.Account;
import com.jimmyproject.accountservice.enums.AccountStatus;
import com.jimmyproject.accountservice.exceptions.InsufficientFundsException;
import com.jimmyproject.accountservice.exceptions.ResourceNotFoundException;
import com.jimmyproject.accountservice.mapper.AccountMapper;
import com.jimmyproject.accountservice.repository.AccountRepository;
import com.jimmyproject.accountservice.web.CustomerClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Implementation of the AccountService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final CustomerClient customerClient;

    @Override
    @Transactional
    public AccountResponseDto createAccount(AccountRequestDto requestDto) {
        log.info("Creating new account for customer ID: {}", requestDto.getCustomerId());
        
        // Map DTO to entity and save
        Account account = accountMapper.toEntity(requestDto);
        // Set initial status if not provided
        if (account.getAccountStatus() == null) {
            account.setAccountStatus(AccountStatus.ACTIVE);
        }
        
        Account savedAccount = accountRepository.save(account);
        log.info("Created account with ID: {}", savedAccount.getId());
        
        return accountMapper.toResponseDto(savedAccount);
    }

    @Override
    public AccountResponseDto getAccountById(Long id) {
        log.debug("Fetching account with ID: {}", id);
        Account account = findAccountOrThrow(id);
        return accountMapper.toResponseDto(account);
    }

    @Override
    public List<AccountResponseDto> getAllAccounts() {
        log.debug("Fetching all accounts");
        return accountRepository.findAll().stream()
                .map(accountMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public AccountResponseDto updateAccount(Long id, AccountRequestDto requestDto) {
        log.info("Updating account with ID: {}", id);
        
        Account existingAccount = findAccountOrThrow(id);
        accountMapper.updateEntityFromDto(requestDto, existingAccount);
        
        Account updatedAccount = accountRepository.save(existingAccount);
        log.info("Updated account with ID: {}", id);
        
        return accountMapper.toResponseDto(updatedAccount);
    }

    @Override
    @Transactional
    public void deleteAccount(Long id) {
        log.info("Deleting account with ID: {}", id);
        if (!accountRepository.existsById(id)) {
            throw new ResourceNotFoundException("Account", id);
        }
        accountRepository.deleteById(id);
        log.info("Deleted account with ID: {}", id);
    }


    @Override
    @CircuitBreaker(name="customerService",fallbackMethod = "getAccountWithCustomerFallback")
    public CustomerResponseDto getAccountWithCustomer(Long id) {
        log.info("Fetched customer with id {}", id);
        var account = findAccountOrThrow(id);
        var customerId = account.getCustomerId();
        log.info("Fetching customer with id {}", customerId);
        return customerClient.getCustomerById(customerId);

    }
    private String getAccountWithCustomerFallback(Long id,Throwable t) {
        log.error("Failed to fetch customer with id {}", id, t);
        return String.format("Service Unavailable for customer with id {}",id);
    }
    @Override
    @CircuitBreaker(name="customerService",fallbackMethod = "getAccountById")
    public AccountCustomerResponse getAccountWithCustomerAndAccount(Long id) {

        Account account = findAccountOrThrow(id);
        CustomerResponseDto customer = getAccountWithCustomer(id);
        return AccountCustomerResponse.builder()
                .account(accountMapper.toResponseDto(account))
                .customer(customer)
                .build();
    }

    @Override
    @Transactional
    public AccountResponseDto updateAccountStatus(Long id, AccountStatus status) {
        log.info("Updating status to {} for account ID: {}", status, id);
        
        Account account = findAccountOrThrow(id);
        account.setAccountStatus(status);
        
        Account updatedAccount = accountRepository.save(account);
        log.info("Updated status to {} for account ID: {}", status, id);
        
        return accountMapper.toResponseDto(updatedAccount);
    }

    @Override
    public List<AccountResponseDto> getAccountsByCustomerId(Long customerId) {
        log.debug("Fetching all accounts for customer ID: {}", customerId);
        return accountRepository.findByCustomerId(customerId)
                .stream()
                .map(accountMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public AccountResponseDto updateBalance(Long id, BigDecimal amount) {
        log.info("Updating balance by {} for account ID: {}", amount, id);
        
        Account account = findAccountOrThrow(id);
        
        if (amount.compareTo(BigDecimal.ZERO) < 0 && 
            account.getBalance().compareTo(amount.abs()) < 0) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal");
        }
        
        account.setBalance(account.getBalance().add(amount));
        Account updatedAccount = accountRepository.save(account);
        
        log.info("Updated balance to {} for account ID: {}", updatedAccount.getBalance(), id);
        return accountMapper.toResponseDto(updatedAccount);
    }

    @Override
    @Transactional
    public void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        log.info("Transferring {} from account ID: {} to account ID: {}", amount, fromAccountId, toAccountId);
        
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }
        
        // Withdraw from source account
        updateBalance(fromAccountId, amount.negate());
        
        try {
            // Deposit to target account
            updateBalance(toAccountId, amount);
        } catch (Exception e) {
            // Rollback the withdrawal if deposit fails
            log.error("Transfer failed, rolling back withdrawal from account ID: {}", fromAccountId);
            updateBalance(fromAccountId, amount); // Add the amount back
            throw e;
        }
    }

    /**
     * Helper method to find an account by ID or throw exception if not found.
     *
     * @param id the account ID
     * @return the found account
     * @throws ResourceNotFoundException if account not found
     */
    private Account findAccountOrThrow(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account",id));
    }
}
