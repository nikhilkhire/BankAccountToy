package com.code.factory.banktoy.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.code.factory.controller.AccountsRestController;
import com.code.factory.domain.Account;
import com.code.factory.domain.LoginCredentials;
import com.code.factory.domain.UserProfileEntity;
import com.code.factory.enums.AccountType;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountsRestController.class)
public class BalanceControllerTests extends BaseControllerTests{

    @Autowired
    private MockMvc mvc;

    @Test
    public void testBalance() throws Exception{
        given(this.accountService.checkBalance(Mockito.any())).willReturn(this.createAccount());
        final Gson gson = new Gson();
        final String json = gson.toJson(this.createLoginCredentials());
        this.mvc.perform(post("/balanceCheck").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(
                        content().json(
                                "{\"success\":true,\"messages\":{},\"errors\":{},\"data\":{\"balance\":\"€100\"},\"httpResponseCode\":200}"));
    }

    @Test
    public void testDeposit() throws Exception{
        given(this.accountService.deposit(Mockito.any(), Mockito.any())).willReturn(true);
        final Gson gson = new Gson();
        final String json = gson.toJson(this.createLoginCredentials());
        this.mvc.perform(post("/deposit").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(
                        content().json(
                                "{\"success\":true,\"messages\":{},\"errors\":{},\"data\":{\"deposited\":\"15 in 778855441122\"},\"httpResponseCode\":200}"));
    }

    @Test
    public void testTransfer() throws Exception{
        given(this.accountService.transfer(Mockito.any(), Mockito.any())).willReturn(true);
        final Gson gson = new Gson();
        final String json = gson.toJson(this.createLoginCredentials());
        this.mvc.perform(post("/transfer").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(
                        content().json(
                                "{\"success\":true,\"messages\":{},\"errors\":{},\"data\":{\"transferred\":15},\"httpResponseCode\":200}"));
    }

    private LoginCredentials createLoginCredentials(){
        final LoginCredentials loginCredentials = new LoginCredentials();
        loginCredentials.setIbanFrom("123456789");
        loginCredentials.setIbanTo("778855441122");
        loginCredentials.setAmount(BigDecimal.valueOf(15));
        loginCredentials.setUserName("nikhilk");
        loginCredentials.setPassword("554466554466");
        return loginCredentials;
    }

    private Optional<Account> createAccount(){
        final Account account = new Account();
        account.setType(AccountType.SAVING);
        account.setIban("987456654789");
        account.setCurrency("€");
        account.setAmount(BigDecimal.valueOf(100));
        return Optional.of(account);
    }

    private UserProfileEntity createUserProfile(){
        final UserProfileEntity userProfile = new UserProfileEntity();
        userProfile.setAccountList(Arrays.asList(this.createAccount().get()));
        userProfile.setToken("554466554466");
        userProfile.setSalt("12345");
        userProfile.setUserId("1");
        userProfile.setUserName("nikhilk");
        userProfile.setUserPassword("qwertzuiop");
        return userProfile;
    }

}