package com.abcinvestmentbank.generalledger.domain;

public enum AccountType {

    E("External"),
    I("Internal");

    String description;

    AccountType(String description){
        this.description = description;}

    public String getDescription() {
        return description;
    }

}
