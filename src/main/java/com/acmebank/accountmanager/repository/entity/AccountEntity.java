package com.acmebank.accountmanager.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@Entity(name="account")
public class AccountEntity extends BaseEntity{

    @Id
    private Long id;
    private BigDecimal balance;
}
