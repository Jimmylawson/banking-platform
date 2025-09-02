package com.jimmyproject.accountservice.repository;

import com.jimmyproject.accountservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface AccountRepository  extends JpaRepository<Account, Long> {
    List<Account> findByCustomerId(Long customerId);
}
