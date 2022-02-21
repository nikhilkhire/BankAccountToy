package com.code.factory.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "Transactions")
public class Transactions implements Serializable{

    private static final long serialVersionUID = 1601457588197297887L;
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long              id;
    @Column(name = "amount")
    private BigDecimal        amount;
    @Column(name = "type")
    private Integer           type;
    @ManyToOne
    private Account           account;

    public Long getId(){
        return this.id;
    }

    public void setId(final Long id){
        this.id = id;
    }

    public BigDecimal getAmount(){
        return this.amount;
    }

    public void setAmount(final BigDecimal amount){
        this.amount = amount;
    }

    public Integer getType(){
        return this.type;
    }

    public void setType(final Integer type){
        this.type = type;
    }

    public Account getAccount(){
        return this.account;
    }

    public void setAccount(final Account account){
        this.account = account;
    }
}
