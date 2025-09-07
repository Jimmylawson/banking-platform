package com.jimmyproject.accountservice.web;


import com.jimmyproject.accountservice.dtos.AccountRequestDto;
import com.jimmyproject.accountservice.dtos.CustomerCreatedEvent;
import com.jimmyproject.accountservice.enums.AccountStatus;
import com.jimmyproject.accountservice.enums.AccountType;
import com.jimmyproject.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomerEventConsumer {
    private final AccountService accountService;

    @Bean
    public Consumer<CustomerCreatedEvent> handleCustomerCreated(){
        return event->{
            try{
                log.info("Customer created event received!");
                AccountRequestDto requestDto = AccountRequestDto.builder()
                        .customerId(event.customerId())
                        .accountNumber(generateRandomAccountNumber())
                        .accountType(AccountType.CHECKING)
                        .accountStatus(AccountStatus.ACTIVE)
                        .balance(new BigDecimal(0))
                        .build();
                accountService.createAccount(requestDto);
                log.info("Successfully created account for customer {}", event.email());
            }catch(Exception e){
                log.error("Error occured while  creating account for {}", event.email());

            }

        };
    }

    private String generateRandomAccountNumber() {
        // Generate a 10-digit account number
        long number = (long) (Math.random() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(number);
    }
}
