package com.code.factory.controller;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.code.factory.domain.Account;
import com.code.factory.domain.LoginCredentials;
import com.code.factory.util.StandardJsonResponse;
import com.code.factory.util.StandardJsonResponseImpl;

@RestController
public class AccountsRestController extends BaseController{

    @PostMapping("/balanceCheck")
    public StandardJsonResponse getAccountBalance(@RequestBody final LoginCredentials loginCredentials){
        final StandardJsonResponse jsonResponse = new StandardJsonResponseImpl();
        final HashMap<String, Object> responseData = new HashMap<>();

        final Optional<Account> account = this.accountService.checkBalance(loginCredentials);
        if(account.isEmpty()){
            jsonResponse.setSuccess(false, "User authentication failed", StandardJsonResponse.RESOURCE_NOT_FOUND_MSG);
            jsonResponse.setHttpResponseCode(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value());
            return jsonResponse;
        }

        responseData.put("balance", account.get().getCurrency() + account.get().getAmount());

        jsonResponse.setSuccess(true);
        jsonResponse.setData(responseData);
        jsonResponse.setHttpResponseCode(HttpStatus.OK.value());

        return jsonResponse;
    }

    @PostMapping("/deposit")
    public StandardJsonResponse depositMoney(@RequestBody final LoginCredentials loginCredentials){
        final StandardJsonResponse jsonResponse = new StandardJsonResponseImpl();
        final HashMap<String, Object> responseData = new HashMap<>();

        if(this.accountService.deposit(loginCredentials, jsonResponse)){
            responseData.put("deposited", loginCredentials.getAmount() + " in " + loginCredentials.getIbanTo());

            jsonResponse.setSuccess(true);
            jsonResponse.setData(responseData);
            jsonResponse.setHttpResponseCode(HttpStatus.OK.value());
        }else{
            jsonResponse.setSuccess(false, "User/Account not found", StandardJsonResponse.RESOURCE_NOT_FOUND_MSG);
            jsonResponse.setHttpResponseCode(HttpStatus.NOT_MODIFIED.ordinal());
        }
        return jsonResponse;

    }

    @PostMapping("/transfer")
    public StandardJsonResponse transferMoney(@RequestBody final LoginCredentials loginCredentials){
        final StandardJsonResponse jsonResponse = new StandardJsonResponseImpl();
        final HashMap<String, Object> responseData = new HashMap<>();
        if(this.accountService.transfer(loginCredentials, jsonResponse)){
            responseData.put("transferred", loginCredentials.getAmount());
            jsonResponse.setSuccess(true);
            jsonResponse.setData(responseData);
            jsonResponse.setHttpResponseCode(HttpStatus.OK.value());
        }
        return jsonResponse;
    }

    @PostMapping("/transactionHistory")
    public StandardJsonResponse transactionHistory(@RequestBody final LoginCredentials loginCredentials){
        final StandardJsonResponse jsonResponse = new StandardJsonResponseImpl();
        final HashMap<String, Object> responseData = new HashMap<>();
        if(this.accountService.fetchTransactionHistory(loginCredentials, jsonResponse, responseData)){
            jsonResponse.setSuccess(true);
            jsonResponse.setData(responseData);
            jsonResponse.setHttpResponseCode(HttpStatus.OK.value());
        }
        return jsonResponse;
    }
}
