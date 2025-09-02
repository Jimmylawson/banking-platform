package com.jimmyproject.customerservice.web;

import com.jimmyproject.customerservice.dto.CustomerRequestDto;
import com.jimmyproject.customerservice.dto.CustomerResponseDto;
import com.jimmyproject.customerservice.enums.CustomerStatus;
import com.jimmyproject.customerservice.service.CustomerServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * REST controller for managing customers.
 */
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServiceInterface customerService;

    /**
     * Create a new customer.
     *
     * @param requestDto the customer to create
     * @return the created customer with status 201 (Created)
     */
    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(@Valid @RequestBody CustomerRequestDto requestDto) {
        CustomerResponseDto response = customerService.createCustomer(requestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    /**
     * Get a customer by ID.
     *
     * @param id the ID of the customer to retrieve
     * @return the customer with status 200 (OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomer(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    /**
     * Get all customers with pagination.
     *
     * @param pageable pagination information
     * @return list of customers with status 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers(Pageable pageable) {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    /**
     * Update a customer.
     *
     * @param id the ID of the customer to update
     * @param requestDto the customer details to update
     * @return the updated customer with status 200 (OK)
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequestDto requestDto) {
        return ResponseEntity.ok(customerService.updateCustomer(id, requestDto));
    }

    /**
     * Delete a customer by ID.
     *
     * @param id the ID of the customer to delete
     * @return status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Update a customer's status.
     *
     * @param id the ID of the customer to update
     * @param status the new status
     * @return the updated customer with status 200 (OK)
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<CustomerResponseDto> updateCustomerStatus(
            @PathVariable Long id,
            @RequestParam CustomerStatus status) {
        return ResponseEntity.ok(customerService.updateCustomerStatus(id, status));
    }

    /**
     * Check if an email is already in use.
     *
     * @param email the email to check
     * @return true if the email is in use, false otherwise
     */
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        return ResponseEntity.ok(customerService.existsByEmail(email));
    }

    /**
     * Check if a phone number is already in use.
     *
     * @param phoneNumber the phone number to check
     * @return true if the phone number is in use, false otherwise
     */
    @GetMapping("/check-phone")
    public ResponseEntity<Boolean> checkPhoneNumberExists(@RequestParam String phoneNumber) {
        return ResponseEntity.ok(customerService.existsByPhoneNumber(phoneNumber));
    }
}
