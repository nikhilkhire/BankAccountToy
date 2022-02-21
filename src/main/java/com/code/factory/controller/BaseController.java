package com.code.factory.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.code.factory.services.AccountService;
import com.code.factory.services.AuthenticationService;

public class BaseController{
    protected final Log   logger = LogFactory.getLog(this.getClass());

    @Autowired
    AccountService        accountService;

    @Autowired
    AuthenticationService authenticationService;

}
