package com.acmebank.accountmanager.repository.entity;

import com.acmebank.accountmanager.model.TransferStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@NoArgsConstructor
@Getter
@Setter
@Entity(name="transfer")
public class TransferEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Long fromAccountId;
    private Long toAccountId;

    private BigDecimal amount;
    private TransferStatus status;

    private String reference;

    @Temporal(TemporalType.TIMESTAMP)
    private Date transferTime;
}
