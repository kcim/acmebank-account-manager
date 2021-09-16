package com.acmebank.accountmanager.model;

public class NotEnoughBalanceException extends Exception{

    public NotEnoughBalanceException(String message){
        super(message);
    }
}
