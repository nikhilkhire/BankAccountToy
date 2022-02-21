package com.code.factory.banktoy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.code.factory.services.AccountService;
import com.code.factory.services.AuthenticationService;

public class BaseControllerTests{

    @Autowired
    protected MockMvc               mvc;

    @MockBean
    protected AccountService        accountService;

    @MockBean
    protected AuthenticationService authenticationService;

}