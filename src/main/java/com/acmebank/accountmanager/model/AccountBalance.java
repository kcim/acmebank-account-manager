package com.acmebank.accountmanager.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountBalance {

    private BigDecimal balance;

}
