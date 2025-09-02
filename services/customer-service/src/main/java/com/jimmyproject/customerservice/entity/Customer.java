package com.jimmyproject.customerservice.entity;


import com.jimmyproject.customerservice.enums.CustomerStatus;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "customers")
public class Customer extends BaseEntity{
    @Column(nullable = false, length = 100)
    private String customerName;
    @Column(nullable = false, unique = true,length = 255)
    private String email;
    @Column(unique = true,nullable = false,length = 12)
    private String phoneNumber;
    @Column(nullable = false, length = 255)
    private String address;
    @Enumerated(EnumType.STRING)
    private CustomerStatus status = CustomerStatus.ACTIVE;


}


