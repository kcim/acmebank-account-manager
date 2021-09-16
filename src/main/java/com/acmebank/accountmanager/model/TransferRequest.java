package com.acmebank.accountmanager.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransferRequest {

    private Long fromAccountId;
    private Long toAccountId;
    private String reference;
    private BigDecimal amount;

}
