package com.code.factory.enums;

public enum AccountType{
    CHECKING(1), SAVING(2), LOAN(3);

    int id;

    private AccountType(final int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

}
