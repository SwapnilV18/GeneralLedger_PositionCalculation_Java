package com.abcinvestmentbank.generalledger.service;

import com.abcinvestmentbank.generalledger.domain.Position;
import com.abcinvestmentbank.generalledger.domain.Transaction;
import com.abcinvestmentbank.generalledger.exception.TransactionFileException;

import java.io.File;
import java.util.List;

public interface PositionTransactionInputOutputService {

    //JSON key constants
    String TransactionIdKey = "TransactionId";
    String InstrumentKey = "Instrument";
    String TransactionTypeKey = "TransactionType";
    String TransactionQuantityKey = "TransactionQuantity";

    //Output file headers
    String PositionEODHeaders = "Instrument,Account,AccountType,Quantity,Delta";

    List<Position> readStartOfDayPositions(File startOfDayPositionsFile);

    List<Transaction> readTransactions(File transactionsFile) throws TransactionFileException;

    void writePositionsEOD(List<Position> positionEODList, File outputFile);

}
