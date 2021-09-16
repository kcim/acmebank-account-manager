package com.acmebank.accountmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ApiError {

    private Integer errorCode;
    private Date timestamp;
    private String message;
}
