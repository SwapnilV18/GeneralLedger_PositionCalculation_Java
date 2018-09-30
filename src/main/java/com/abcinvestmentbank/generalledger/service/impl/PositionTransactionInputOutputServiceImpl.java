package com.abcinvestmentbank.generalledger.service.impl;

import com.abcinvestmentbank.generalledger.domain.*;
import com.abcinvestmentbank.generalledger.exception.TransactionFileException;
import com.abcinvestmentbank.generalledger.service.FileHandlerService;
import com.abcinvestmentbank.generalledger.service.PositionTransactionInputOutputService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PositionTransactionInputOutputServiceImpl implements PositionTransactionInputOutputService {

    @Autowired
    FileHandlerService fileHandlerService;

    @Override
    public List<Position> readStartOfDayPositions(File startOfDayPositionsFile) {

        List<String> inputStringList = fileHandlerService.readToStringList(startOfDayPositionsFile);
        // remove headers
        inputStringList.remove(0);


        return inputStringList.stream().map(line -> {
            int recordCount = 0;
            //remove spaces if any.
            line = line.replaceAll(" ", "");

            List<String> positonInputRow = Arrays.asList(line.split(","));

            Position pos = new Position();
            recordCount++;
            try {
                pos.setInstrument(Instrument.valueOf(positonInputRow.get(0)));
                pos.setAccount(Long.parseLong(positonInputRow.get(1)));
                pos.setAccountType(AccountType.valueOf(positonInputRow.get(2)));
                pos.setQuantity(Long.parseLong(positonInputRow.get(3)));
            } catch (Exception e) {
                log.error("Position record %s has caused some exception : %s", recordCount, e.getMessage());
                throw e;
            }
            return pos;
        }).collect(Collectors.toList());

    }

    @Override
    public List<Transaction> readTransactions(File transactionsFile) throws TransactionFileException {

        List<Transaction> transactions = new ArrayList<>();
        String transactionsJson;
        try {
            transactionsJson = IOUtils.toString(new FileInputStream(transactionsFile), "UTF-8");
        } catch (IOException e) {
            log.error("Exception when reading transaction file, %s", e.getMessage());
            throw new TransactionFileException("Exception when reading transaction file",e);
        }

        JSONArray jsonTransactionArray = new JSONArray(transactionsJson);

        for (int count = 0, size = jsonTransactionArray.length(); count < size; count++) {

            JSONObject transactionInArray = jsonTransactionArray.getJSONObject(count);

            Transaction transaction = new Transaction();
            transaction.setTransactionId(transactionInArray.getLong(TransactionIdKey));
            transaction.setInstrument(Instrument.valueOf(transactionInArray.getString(InstrumentKey)));
            transaction.setTransactionType(TransactionType.valueOf(transactionInArray.getString(TransactionTypeKey)));
            transaction.setTransactionQuantity(transactionInArray.getLong(TransactionQuantityKey));

            transactions.add(transaction);
        }
        return transactions;

    }


    @Override
    public void writePositionsEOD(List<Position> positionEODList, File outputFile) {
        StringBuilder positionsEOD = new StringBuilder(PositionEODHeaders + "\n");
        positionEODList.stream().forEach(
                position -> positionsEOD.append(position + "\n")
        );
        fileHandlerService.writeToFile(positionsEOD, outputFile);

    }
}
