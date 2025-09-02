package com.jimmyproject.transactionservice.dtos;

import com.jimmyproject.transactionservice.enums.PaymentType;
import com.jimmyproject.transactionservice.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for returning transaction details in responses.
 * Matches the Transaction entity structure with additional calculated fields.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDto {
    private Long id;
    private Long sourceAccountId;
    private Long destinationAccountId;
    private BigDecimal amount;
    private String currency;
    private PaymentType paymentType;
    private TransactionStatus status;
    private String referenceId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Helper method to provide a user-friendly status message
    public String getStatusMessage() {
        if (status == null) {
            return "Transaction status unknown";
        }
        return String.format("Transaction %s %s", 
            status.toString().toLowerCase(),
            status == TransactionStatus.COMPLETED ? "successfully" : "");
    }
}
