package com.jimmyproject.accountservice.entity;


import com.jimmyproject.accountservice.enums.AccountStatus;
import com.jimmyproject.accountservice.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "accounts")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class Account  extends BaseEntity{
    private String accountNumber;
    private  Long customerId;
    private BigDecimal balance = BigDecimal.ZERO;
    private String currency;
    @Enumerated(EnumType.STRING)
    private AccountType accountType = AccountType.CHECKING;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus accountStatus = AccountStatus.ACTIVE;


}
