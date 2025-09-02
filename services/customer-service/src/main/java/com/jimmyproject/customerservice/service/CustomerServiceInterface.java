package com.jimmyproject.customerservice.service;

import com.jimmyproject.customerservice.dto.CustomerRequestDto;
import com.jimmyproject.customerservice.dto.CustomerResponseDto;
import com.jimmyproject.customerservice.enums.CustomerStatus;

import java.util.List;

/**
 * Service interface for customer-related operations.
 */
public interface CustomerServiceInterface {
    
    /**
     * Create a new customer.
     *
     * @param requestDto the customer data to create
     * @return the created customer
     */
    CustomerResponseDto createCustomer(CustomerRequestDto requestDto);

    /**
     * Get a customer by ID.
     *
     * @param id the customer ID
     * @return the customer data
     * @throws com.jimmyproject.customerservice.exceptions.ResourceNotFoundException if customer not found
     */
    CustomerResponseDto getCustomerById(Long id);

    /**
     * Get all customers.
     *
     * @return list of all customers
     */
    List<CustomerResponseDto> getAllCustomers();

    /**
     * Update a customer.
     *
     * @param id the customer ID
     * @param requestDto the customer data to update
     * @return the updated customer
     * @throws com.jimmyproject.customerservice.exceptions.ResourceNotFoundException if customer not found
     */
    CustomerResponseDto updateCustomer(Long id, CustomerRequestDto requestDto);

    /**
     * Delete a customer by ID.
     *
     * @param id the customer ID to delete
     */
    void deleteCustomer(Long id);

    /**
     * Update customer status.
     *
     * @param id the customer ID
     * @param status the new status
     * @return the updated customer
     * @throws com.jimmyproject.customerservice.exceptions.ResourceNotFoundException if customer not found
     */
    CustomerResponseDto updateCustomerStatus(Long id, CustomerStatus status);

    /**
     * Check if a customer exists by email.
     *
     * @param email the email to check
     * @return true if customer exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Check if a customer exists by phone number.
     *
     * @param phoneNumber the phone number to check
     * @return true if customer exists, false otherwise
     */
    boolean existsByPhoneNumber(String phoneNumber);
}
