package com.jimmyproject.customerservice.service;

import com.jimmyproject.customerservice.dto.CustomerRequestDto;
import com.jimmyproject.customerservice.dto.CustomerResponseDto;
import com.jimmyproject.customerservice.dtos.CustomerCreatedEvent;
import com.jimmyproject.customerservice.entity.Customer;
import com.jimmyproject.customerservice.enums.CustomerStatus;
import com.jimmyproject.customerservice.exceptions.ResourceNotFoundException;
import com.jimmyproject.customerservice.mapper.CustomerMapper;
import com.jimmyproject.customerservice.repository.CustomerRepository;
import com.jimmyproject.customerservice.service.CustomerServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the CustomerServiceInterface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerServiceInterface {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final StreamBridge streamBridge;

    @Override
    @Transactional
    public CustomerResponseDto createCustomer(CustomerRequestDto requestDto) {
        log.info("Creating new customer with email: {}", requestDto.getEmail());
        
        // Check if email already exists
        if (customerRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("Email already in use: " + requestDto.getEmail());
        }
        
        // Check if phone number already exists
        if (customerRepository.existsByPhoneNumber(requestDto.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number already in use: " + requestDto.getPhoneNumber());
        }
        
        Customer customer = customerMapper.toEntity(requestDto);
        Customer savedCustomer = customerRepository.save(customer);
        //Publish event
        var event = new CustomerCreatedEvent(
                "customer_created",
                savedCustomer.getId(),
                savedCustomer.getEmail(),
                Instant.now()
        );

        //Send to kafka
        streamBridge.send("customer-created-out-0", event);

        log.info("Created customer with ID: {}", savedCustomer.getId());
        
        return customerMapper.toResponseDto(savedCustomer);
    }

    @Override
    public CustomerResponseDto getCustomerById(Long id) {
        log.debug("Fetching customer with ID: {}", id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        return customerMapper.toResponseDto(customer);
    }

    @Override
    public List<CustomerResponseDto> getAllCustomers() {
        log.debug("Fetching all customers");
        return customerRepository.findAll().stream()
                .map(customerMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CustomerResponseDto updateCustomer(Long id, CustomerRequestDto requestDto) {
        log.info("Updating customer with ID: {}", id);
        
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        
        // Check if email is being changed and if it's already in use
        if (!existingCustomer.getEmail().equals(requestDto.getEmail()) && 
            customerRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("Email already in use: " + requestDto.getEmail());
        }
        
        // Check if phone number is being changed and if it's already in use
        if (!existingCustomer.getPhoneNumber().equals(requestDto.getPhoneNumber()) && 
            customerRepository.existsByPhoneNumber(requestDto.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number already in use: " + requestDto.getPhoneNumber());
        }
        
        customerMapper.updateEntityFromDto(requestDto, existingCustomer);
        Customer updatedCustomer = customerRepository.save(existingCustomer);
        log.info("Updated customer with ID: {}", id);
        
        return customerMapper.toResponseDto(updatedCustomer);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        log.info("Deleting customer with ID: {}", id);
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer", "id", id);
        }
        customerRepository.deleteById(id);
        log.info("Deleted customer with ID: {}", id);
    }

    @Override
    @Transactional
    public CustomerResponseDto updateCustomerStatus(Long id, CustomerStatus status) {
        log.info("Updating status to {} for customer ID: {}", status, id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
                
        customer.setStatus(status);
        Customer updatedCustomer = customerRepository.save(customer);
        log.info("Updated status to {} for customer ID: {}", status, id);
        
        return customerMapper.toResponseDto(updatedCustomer);
    }

    @Override
    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return customerRepository.existsByPhoneNumber(phoneNumber);
    }
}
