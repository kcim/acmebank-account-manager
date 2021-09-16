package com.acmebank.accountmanager.service;


import com.acmebank.accountmanager.model.NotEnoughBalanceException;
import com.acmebank.accountmanager.repository.entity.TransferEntity;

public interface TransferService {

    void save(TransferEntity request);
    TransferEntity transferById(Long id) throws NotEnoughBalanceException;

}
