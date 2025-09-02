package com.jimmyproject.customerservice.dtos;


import com.jimmyproject.customerservice.enums.CustomerStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class customerResponseDto {
    private String customerNamee;
    private String email;
    private String phoneNumber;
    private String address;
    private CustomerStatus status;

}
