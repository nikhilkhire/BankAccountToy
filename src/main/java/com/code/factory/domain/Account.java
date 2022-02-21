package com.code.factory.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.code.factory.enums.AccountType;

@Entity
public class Account implements Serializable{

    private static final long  serialVersionUID = -1720138270975798730L;
    @Id
    @Column(name = "IBAN")
    private String             iban;
    @Column(name = "amount")
    private BigDecimal         amount;
    @Column(name = "type")
    private AccountType        type;
    @Column(name = "currency")
    private String             currency;
    @ManyToOne
    private UserProfileEntity  userId;
    @OneToMany
    @Column(name = "transactionIds")
    private List<Transactions> transactionsList;

    public String getIban(){
        return this.iban;
    }

    public void setIban(final String iban){
        this.iban = iban;
    }

    public BigDecimal getAmount(){
        return this.amount;
    }

    public void setAmount(final BigDecimal amount){
        this.amount = amount;
    }

    public AccountType getType(){
        return this.type;
    }

    public void setType(final AccountType type){
        this.type = type;
    }

    public UserProfileEntity getUserId(){
        return this.userId;
    }

    public void setUserId(final UserProfileEntity userId){
        this.userId = userId;
    }

    public String getCurrency(){
        return this.currency;
    }

    public void setCurrency(final String currency){
        this.currency = currency;
    }
}
