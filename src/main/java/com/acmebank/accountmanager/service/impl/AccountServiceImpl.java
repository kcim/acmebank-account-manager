package com.acmebank.accountmanager.service.impl;

import com.acmebank.accountmanager.repository.AccountRepository;
import com.acmebank.accountmanager.repository.entity.AccountEntity;
import com.acmebank.accountmanager.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;


    @Override
    public AccountEntity getAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Account not found"));
    }
}
