package com.jimmyproject.accountservice.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CustomerResponseDto {
    private String customerName;
    private String email;
    private String phoneNumber;
    private String address;

}
