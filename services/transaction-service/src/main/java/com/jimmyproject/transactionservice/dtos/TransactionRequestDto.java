package com.jimmyproject.transactionservice.dtos;

import com.jimmyproject.transactionservice.enums.PaymentType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDto {
    @NotNull(message = "Source account ID is required")
    private Long sourceAccountId;
    
    @NotNull(message = "Destination account ID is required")
    private Long destinationAccountId;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0001", message = "Amount must be greater than 0")
    @Digits(integer = 15, fraction = 4, message = "Amount must have up to 15 digits before the decimal and 4 decimal places")
    private BigDecimal amount;
    
    @NotBlank(message = "Currency is required")
    @Size(min = 3, max = 3, message = "Currency code must be 3 characters")
    private String currency;
    
    @NotNull(message = "Payment type is required")
    private PaymentType paymentType;
    
    @Size(max = 64, message = "Reference ID must be less than 64 characters")
    private String referenceId;
}
