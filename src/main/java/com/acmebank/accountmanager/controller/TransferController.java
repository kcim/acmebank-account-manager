package com.acmebank.accountmanager.controller;

import com.acmebank.accountmanager.model.NotEnoughBalanceException;
import com.acmebank.accountmanager.model.TransferRequest;
import com.acmebank.accountmanager.model.TransferResponse;
import com.acmebank.accountmanager.model.TransferStatus;
import com.acmebank.accountmanager.repository.entity.TransferEntity;
import com.acmebank.accountmanager.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("transfer")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @GetMapping("{fromId}/{toId}/{amount}")
    public TransferResponse transfer(@PathVariable("fromId") Long fromAccountId,
                                     @PathVariable("toId") Long toAccountId,
                                     @PathVariable("amount") BigDecimal amount){

        TransferEntity transferEntity = TransferEntity.builder()
                .fromAccountId(fromAccountId)
                .toAccountId(toAccountId)
                .amount(amount).build();

        return doTransfer(transferEntity);
    }

    @PostMapping()
    public TransferResponse transfer(@RequestBody TransferRequest transferRequest){

        TransferEntity transferEntity = TransferEntity.builder()
                .fromAccountId(transferRequest.getFromAccountId())
                .toAccountId(transferRequest.getToAccountId())
                .amount(transferRequest.getAmount())
                .reference(transferRequest.getReference())
                .build();

        return doTransfer(transferEntity);
    }

    private TransferResponse doTransfer(TransferEntity transferEntity) {
        transferService.save(transferEntity);
        try {
            transferService.transferById(transferEntity.getId());
        } catch (NotEnoughBalanceException e) {
            transferEntity.setStatus(TransferStatus.CANCELLED);
            transferService.save(transferEntity);
            return TransferResponse.builder().status(e.getMessage()).build();
        }
        return TransferResponse.builder().status("Transferred successfully").build();
    }

}
