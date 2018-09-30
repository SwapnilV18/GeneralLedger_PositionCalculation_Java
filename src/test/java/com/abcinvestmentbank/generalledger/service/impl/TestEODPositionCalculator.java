package com.abcinvestmentbank.generalledger.service.impl;

import com.abcinvestmentbank.generalledger.domain.*;
import com.abcinvestmentbank.generalledger.service.EODPositionCalculatorService;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestEODPositionCalculator {

    Transaction transaction;
    Position positionSODBuy, positionEODBuy, positionSODSell, positionEODSell;

    EODPositionCalculatorService EODPositionCalculatorService;

    @BeforeClass
    public void setUp() {

        populateBuyTransactionData();
        populateSellTransactionData();

        transaction = new Transaction();
        transaction.setInstrument(Instrument.APPL);
        transaction.setTransactionId(2l);
        transaction.setTransactionType(TransactionType.S);
        transaction.setTransactionQuantity(200l);

        EODPositionCalculatorService = new EODPositionCalculatorServiceImpl();
    }

    private void populateBuyTransactionData() {
        positionSODBuy = new Position();
        positionSODBuy.setInstrument(Instrument.APPL);
        positionSODBuy.setAccount(101l);
        positionSODBuy.setAccountType(AccountType.E);
        positionSODBuy.setQuantity(10000l);


        positionEODBuy = new Position();
        positionEODBuy.setInstrument(Instrument.APPL);
        positionEODBuy.setAccount(101l);
        positionEODBuy.setAccountType(AccountType.E);
        positionEODBuy.setQuantity(10200l);
        positionEODBuy.setDelta(200l);
    }

    private void populateSellTransactionData() {
        positionSODSell = new Position();
        positionSODSell.setInstrument(Instrument.APPL);
        positionSODSell.setAccount(101l);
        positionSODSell.setAccountType(AccountType.E);
        positionSODSell.setQuantity(10000l);


        positionEODSell = new Position();
        positionEODSell.setInstrument(Instrument.APPL);
        positionEODSell.setAccount(101l);
        positionEODSell.setAccountType(AccountType.E);
        positionEODSell.setQuantity(9800l);
        positionEODSell.setDelta(-200l);
    }

    @Test
    public void testProcessBuyTransaction() {

        EODPositionCalculatorService.processBuyTransaction(transaction, positionSODBuy);
        Assert.assertEquals(positionSODBuy, positionEODBuy);

    }

    @Test
    public void testProcessSellTransaction() {

        EODPositionCalculatorService.processSellTransaction(transaction, positionSODSell);
        Assert.assertEquals(positionSODSell, positionEODSell);

    }

}
