package com.acmebank.accountmanager.service.impl;

import com.acmebank.accountmanager.model.NotEnoughBalanceException;
import com.acmebank.accountmanager.model.TransferStatus;
import com.acmebank.accountmanager.repository.AccountRepository;
import com.acmebank.accountmanager.repository.TransactionRepository;
import com.acmebank.accountmanager.repository.TransferRepository;
import com.acmebank.accountmanager.repository.entity.AccountEntity;
import com.acmebank.accountmanager.repository.entity.TransactionEntity;
import com.acmebank.accountmanager.repository.entity.TransferEntity;
import com.acmebank.accountmanager.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.NoSuchElementException;

@Transactional
@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void save(TransferEntity request) {
        transferRepository.save(request);
    }

    @Override
    public TransferEntity transferById(Long id) throws NotEnoughBalanceException {

        TransferEntity transferEntity = transferRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Transfer not found"));

        Long fromAccountId = transferEntity.getFromAccountId();
        Long toAccountId = transferEntity.getToAccountId();

        AccountEntity fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new NoSuchElementException("Account "+fromAccountId+" not found"));
        AccountEntity toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new NoSuchElementException("Account "+toAccountId+" not found"));

        BigDecimal transferAmount = transferEntity.getAmount();
        BigDecimal fromAccountBalance = fromAccount.getBalance().subtract(transferAmount);
        BigDecimal toAccountBalance = toAccount.getBalance().add(transferAmount);

        if (fromAccountBalance.compareTo(BigDecimal.ZERO) < 0){
            throw new NotEnoughBalanceException("Not enough balance from account "+fromAccountId);
        }

        fromAccount.setBalance(fromAccountBalance);
        toAccount.setBalance(toAccountBalance);

        Date transferTime = new Date();

        TransactionEntity fromTransaction = TransactionEntity.builder()
                .transactionTime(transferTime)
                .amount(transferAmount.negate())
                .reference(String.format("Transfer ID: %d", id))
                .accountID(fromAccountId)
                .balance(fromAccountBalance)
                .build();

        TransactionEntity toTransaction = TransactionEntity.builder()
                .transactionTime(transferTime)
                .amount(transferAmount)
                .reference(String.format("Transfer ID: %d", id))
                .accountID(toAccountId)
                .balance(toAccountBalance)
                .build();

        transferEntity.setStatus(TransferStatus.TRANSFERRED);
        transferEntity.setTransferTime(transferTime);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        transactionRepository.save(fromTransaction);
        transactionRepository.save(toTransaction);
        transferRepository.save(transferEntity);

        return transferEntity;

    }
}
