package com.acmebank.accountmanager.controller;

import com.acmebank.accountmanager.model.AccountBalance;
import com.acmebank.accountmanager.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("{id}")
    public AccountBalance getAccountBalance(@PathVariable("id") Long id){
        return AccountBalance.builder().balance(accountService.getAccountById(id).getBalance()).build();
    }

}
