package com.jimmyproject.transactionservice.entity;


import com.jimmyproject.transactionservice.enums.PaymentType;
import com.jimmyproject.transactionservice.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Setter @Getter
@Entity @Table(name="transactions")
@NoArgsConstructor
@Builder
public class Transaction extends BaseEntity {
    private Long sourceAccountId;
    private Long destinationAccountId;
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal amount;
   @Column(length = 3, nullable = false)
    private String currency;
   @Enumerated(EnumType.STRING)
   @Column(length = 20, nullable = false)
    private PaymentType paymentType;
   @Column(length = 20, nullable = false)
    private TransactionStatus status = TransactionStatus.NEW;
    @Column(name = "reference_id", length = 64)
   private String referenceId;



}
