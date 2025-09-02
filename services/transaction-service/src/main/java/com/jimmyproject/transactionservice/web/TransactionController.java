package com.jimmyproject.transactionservice.web;

import com.jimmyproject.transactionservice.dtos.TransactionRequestDto;
import com.jimmyproject.transactionservice.dtos.TransactionResponseDto;
import com.jimmyproject.transactionservice.enums.TransactionStatus;
import com.jimmyproject.transactionservice.service.IService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final IService service;

    @PostMapping
    public ResponseEntity<TransactionResponseDto> createTransaction(@Valid @RequestBody TransactionRequestDto requestDto) {
        TransactionResponseDto response = service.createTransaction(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> getTransaction(@PathVariable Long id) {
        return ResponseEntity.ok(service.getTransactionById(id));
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDto>> getAllTransactions(Pageable pageable) {
        return ResponseEntity.ok(service.getAllTransactions());
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionResponseDto>> getTransactionsByAccount(
            @PathVariable Long accountId,
            Pageable pageable) {
        return ResponseEntity.ok(service.getTransactionsByAccountId(accountId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TransactionResponseDto> updateTransactionStatus(
            @PathVariable Long id,
            @RequestParam TransactionStatus status) {
        return ResponseEntity.ok(service.updateTransactionStatus(id, status));
    }

    @PostMapping("/process")
    public ResponseEntity<TransactionResponseDto> processTransaction(
            @Valid @RequestBody TransactionRequestDto requestDto) {
        TransactionResponseDto response = service.processTransaction(requestDto);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TransactionResponseDto>> getTransactionsByStatus(
            @PathVariable TransactionStatus status,
            Pageable pageable) {
        return ResponseEntity.ok(service.getTransactionsByStatus(status));
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> checkTransactionExists(@PathVariable Long id) {
        return ResponseEntity.ok(service.transactionExists(id));
    }
}
