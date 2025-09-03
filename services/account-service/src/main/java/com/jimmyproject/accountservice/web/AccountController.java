package com.jimmyproject.accountservice.web;

import com.jimmyproject.accountservice.dtos.AccountRequestDto;
import com.jimmyproject.accountservice.dtos.AccountResponseDto;
import com.jimmyproject.accountservice.enums.AccountStatus;
import com.jimmyproject.accountservice.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponseDto> createAccount(@Valid @RequestBody AccountRequestDto requestDto){
        return ResponseEntity.ok(accountService.createAccount(requestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts(Pageable pageable) {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDto> updateAccount(
            @PathVariable Long id,
            @Valid @RequestBody AccountRequestDto requestDto) {
        return ResponseEntity.ok(accountService.updateAccount(id, requestDto));
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<AccountResponseDto> withdraw(
            @PathVariable Long id,
            @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(accountService.updateBalance(id, amount.negate()));
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(
            @RequestParam Long fromAccountId,
            @RequestParam Long toAccountId,
            @RequestParam BigDecimal amount) {
        accountService.transfer(fromAccountId, toAccountId, amount);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id).getBalance());
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountResponseDto> deposit(
            @PathVariable Long id,
            @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(accountService.updateBalance(id, amount.abs()));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AccountResponseDto> updateAccountStatus(
            @PathVariable Long id,
            @RequestParam AccountStatus status) {
        return ResponseEntity.ok(accountService.updateAccountStatus(id, status));
    }


}
