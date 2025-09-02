package com.jimmyproject.transactionservice.repository;

import com.jimmyproject.transactionservice.entity.Transaction;
import com.jimmyproject.transactionservice.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySourceAccountIdOrDestinationAccountId(Long sourceId, Long destinationId);
    List<Transaction> findByStatus(TransactionStatus status);
}