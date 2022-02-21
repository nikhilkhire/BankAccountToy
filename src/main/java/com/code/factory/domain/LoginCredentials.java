package com.code.factory.domain;

import java.math.BigDecimal;

public class LoginCredentials{
    String     userName;

    String     password;

    String     ibanFrom;

    String     ibanTo;

    BigDecimal amount;

    public String getUserName(){
        return this.userName;
    }

    public void setUserName(final String userName){
        this.userName = userName;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(final String password){
        this.password = password;
    }

    public String getIbanFrom(){
        return this.ibanFrom;
    }

    public void setIbanFrom(final String ibanFrom){
        this.ibanFrom = ibanFrom;
    }

    public String getIbanTo(){
        return this.ibanTo;
    }

    public void setIbanTo(final String ibanTo){
        this.ibanTo = ibanTo;
    }

    public BigDecimal getAmount(){
        return this.amount;
    }

    public void setAmount(final BigDecimal amount){
        this.amount = amount;
    }
}
