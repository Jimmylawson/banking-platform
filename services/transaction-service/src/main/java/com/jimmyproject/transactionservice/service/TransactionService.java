package com.jimmyproject.transactionservice.service;

import com.jimmyproject.transactionservice.dtos.TransactionRequestDto;
import com.jimmyproject.transactionservice.dtos.TransactionResponseDto;
import com.jimmyproject.transactionservice.dtos.TransferMapper;
import com.jimmyproject.transactionservice.entity.Transaction;
import com.jimmyproject.transactionservice.enums.TransactionStatus;
import com.jimmyproject.transactionservice.exceptions.TransactionNotFoundException;
import com.jimmyproject.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionService implements IService {

    private final TransactionRepository transactionRepository;
    private final TransferMapper transferMapper;

    @Override
    @Transactional
    public TransactionResponseDto createTransaction(TransactionRequestDto transactionRequestDto) {
        Transaction transaction = transferMapper.toEntity(transactionRequestDto);
        transaction.setStatus(TransactionStatus.PENDING); // Default status
        Transaction savedTransaction = transactionRepository.save(transaction);
        return transferMapper.toResponseDto(savedTransaction);
    }

    @Override
    public TransactionResponseDto getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found with id: " + id));
        return transferMapper.toResponseDto(transaction);
    }

    @Override
    public List<TransactionResponseDto> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(transferMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponseDto> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findBySourceAccountIdOrDestinationAccountId(accountId, accountId).stream()
                .map(transferMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TransactionResponseDto updateTransactionStatus(Long transactionId, TransactionStatus status) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found with id: " + transactionId));

        transaction.setStatus(status);
        Transaction updatedTransaction = transactionRepository.save(transaction);
        return transferMapper.toResponseDto(updatedTransaction);
    }

    @Override
    @Transactional
    public TransactionResponseDto processTransaction(TransactionRequestDto transactionRequestDto) {
        // Additional business logic for processing transaction
        Transaction transaction = transferMapper.toEntity(transactionRequestDto);
        transaction.setStatus(TransactionStatus.COMPLETED); // Or process through a state machine

        // Here you would typically:
        // 1. Validate the transaction
        // 2. Check account balances
        // 3. Apply business rules
        // 4. Save the transaction

        Transaction savedTransaction = transactionRepository.save(transaction);
        return transferMapper.toResponseDto(savedTransaction);
    }

    @Override
    public List<TransactionResponseDto> getTransactionsByStatus(TransactionStatus status) {
        return transactionRepository.findByStatus(status).stream()
                .map(transferMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean transactionExists(Long transactionId) {
        return transactionRepository.existsById(transactionId);
    }
}