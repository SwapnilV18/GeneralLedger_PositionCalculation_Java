package com.abcinvestmentbank.generalledger.domain;

import lombok.Data;

@Data
public class Transaction {

    Long transactionId;
    Instrument instrument;
    TransactionType transactionType;
    Long transactionQuantity;
}
