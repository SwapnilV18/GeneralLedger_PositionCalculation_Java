package com.abcinvestmentbank.generalledger.domain;

import lombok.Data;

@Data
public class Position {

    Instrument instrument;
    Long account;
    AccountType accountType;
    Long quantity;
    Long delta = 0L;

    @Override
    public String toString(){
        return
                this.instrument.toString() + ","
                +this.account.longValue() + ","
                +this.accountType.toString() + ","
                +this.quantity.longValue() + ","
                +this.delta.longValue();
    }
}
