package com.jimmyproject.accountservice.dtos;


import com.jimmyproject.accountservice.enums.AccountStatus;
import com.jimmyproject.accountservice.enums.AccountType;
import lombok.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountResponseDto {
    private String accountNumber;
    private Long customerId;
    private BigDecimal balance;
    private AccountType accountType;
    private AccountStatus accountStatus;


}
