package com.acmebank.accountmanager.repository.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Version;
import java.util.Date;

@Data
public class BaseEntity {

    @Column(updatable = false)
    @CreatedBy
    private String createdBy = "system";

    @Column(columnDefinition = "TIMESTAMP", updatable = false)
    @CreatedDate
    private Date createdTime;

    @Column(updatable = false)
    @LastModifiedBy
    private String lastUpdatedBy = "system";

    @Column(columnDefinition = "TIMESTAMP", updatable = false)
    @LastModifiedDate
    private Date lastUpdatedTime;

    @Column
    @Version
    private Long version = 0L;


}
