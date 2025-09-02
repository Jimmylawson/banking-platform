package com.jimmyproject.accountservice.dtos;

import com.jimmyproject.accountservice.enums.AccountStatus;
import com.jimmyproject.accountservice.enums.AccountType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO for creating or updating an account.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDto {
    
    private Long id;
    
    @NotBlank(message = "Account number is required")
    @Pattern(regexp = "^[0-9]{10,20}$", message = "Account number must be 10-20 digits")
    private String accountNumber;
    
    @NotNull(message = "Customer ID is required")
    @PositiveOrZero(message = "Customer ID must be a positive number")
    private Long customerId;
    
    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Balance must be greater than or equal to 0")
    @Digits(integer = 15, fraction = 2, message = "Balance must have up to 15 digits before and 2 after decimal")
    private BigDecimal balance;
    
    @NotNull(message = "Account type is required")
    private AccountType accountType;
    
    @NotNull(message = "Account status is required")
    private AccountStatus accountStatus;
}
