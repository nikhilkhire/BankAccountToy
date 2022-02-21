package com.code.factory.enums;

public enum TransactionType{

    DEPOSIT(1), WITHDRAW(2);

    int id;

    private TransactionType(final int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }
}
