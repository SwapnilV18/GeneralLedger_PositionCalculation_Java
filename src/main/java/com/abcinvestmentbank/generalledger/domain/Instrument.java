package com.abcinvestmentbank.generalledger.domain;

public enum Instrument {

    APPL("Apple"),
    IBM("IBM"),
    MSFT("Microsoft"),
    AMZN("Amazon"),
    NFLX("Netflix");

    String name;

    Instrument(String name) {this.name = name;}

    public String getName() {
        return name;
    }

}
