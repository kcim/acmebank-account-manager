package com.acmebank.accountmanager.service;


import com.acmebank.accountmanager.repository.entity.AccountEntity;

public interface AccountService {

    AccountEntity getAccountById(Long id);

}
