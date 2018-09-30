package com.abcinvestmentbank.generalledger.service;

import com.abcinvestmentbank.generalledger.domain.Position;
import com.abcinvestmentbank.generalledger.domain.Transaction;

import java.util.List;

public interface EODPositionCalculatorService {

    List<Position> calculatePositionsEOD(List<Position> positionList, List<Transaction> transactions);

    void processBuyTransaction(Transaction transaction, Position position);

    void processSellTransaction(Transaction transaction, Position position);

}
