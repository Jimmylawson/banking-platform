package com.jimmyproject.accountservice.web;


import com.jimmyproject.accountservice.dtos.CustomerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface CustomerClient {
    @GetMapping(value = "/api/customers/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    CustomerResponseDto getCustomerById(@PathVariable Long id);

}
