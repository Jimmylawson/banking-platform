package com.jimmyproject.accountservice.dtos;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AccountCustomerResponse {
    AccountResponseDto account;
    CustomerResponseDto customer;
}
