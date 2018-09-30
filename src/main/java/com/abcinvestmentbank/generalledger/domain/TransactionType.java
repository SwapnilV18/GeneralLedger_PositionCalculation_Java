package com.abcinvestmentbank.generalledger.domain;

public enum TransactionType {

    B("Buy"),
    S("Sell");

    String description;

    TransactionType(String description){
        this.description = description;}

    public String getDescription() {
        return description;
    }

}
