package com.abcinvestmentbank.generalledger.service.impl;

import com.abcinvestmentbank.generalledger.domain.Instrument;
import com.abcinvestmentbank.generalledger.domain.Position;
import com.abcinvestmentbank.generalledger.domain.Transaction;
import com.abcinvestmentbank.generalledger.service.EODPositionCalculatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

@Slf4j
@Service
public class EODPositionCalculatorServiceImpl implements EODPositionCalculatorService {


    @Override
    public List<Position> calculatePositionsEOD(List<Position> positionList, List<Transaction> transactions) {

        //All positions belonging to a particular Instrument are stored in a Map implementation against the instrument.
        EnumMap<Instrument, List<Position>> positionSODMap = new EnumMap<>(Instrument.class);

        positionList.stream().forEach(
                position -> {
                    List<Position> instrumentPositionSOD = positionSODMap.get(position.getInstrument());
                    if (null == instrumentPositionSOD)
                        instrumentPositionSOD = new ArrayList<>();

                    instrumentPositionSOD.add(position);
                    positionSODMap.put(position.getInstrument(), instrumentPositionSOD);
                }
        );

        // iterate through transactions, process the instrument positions in the transactions . the positions are stored in the positionSODMap

        transactions.stream().forEach(
                transaction -> {
                    //get the concerned instrument and the postions related to it.
                    List<Position> affectedPostions = positionSODMap.get(transaction.getInstrument());

                    positionSODMap.put(transaction.getInstrument(),
                            processPostionsAgainstTransaction(transaction, affectedPostions));
                    // returns position after transaction processing and present delta value

                }
        );
        return positionList;
    }


    List<Position> processPostionsAgainstTransaction(Transaction transaction, List<Position> positions) {

        positions.stream().forEach(
                position -> {
                    switch (transaction.getTransactionType()) {
                        case B:
                            processBuyTransaction(transaction, position);
                            break;

                        case S:
                            processSellTransaction(transaction, position);
                            break;

                    }
                }
        );

        return positions;
    }

    @Override
    public void processBuyTransaction(Transaction transaction, Position position) {
        switch (position.getAccountType()) {
            case E:
                position.setQuantity(position.getQuantity() + transaction.getTransactionQuantity());
                position.setDelta(position.getDelta() + transaction.getTransactionQuantity());
                break;

            case I:
                position.setQuantity(position.getQuantity() - transaction.getTransactionQuantity());
                position.setDelta(position.getDelta() - transaction.getTransactionQuantity());
                break;
        }

    }

    @Override
    public void processSellTransaction(Transaction transaction, Position position) {
        switch (position.getAccountType()) {
            case E:
                position.setQuantity(position.getQuantity() - transaction.getTransactionQuantity());
                position.setDelta(position.getDelta() - transaction.getTransactionQuantity());
                break;

            case I:
                position.setQuantity(position.getQuantity() + transaction.getTransactionQuantity());
                position.setDelta(position.getDelta() + transaction.getTransactionQuantity());
                break;
        }

    }

}
