package com.abcinvestmentbank.generalledger;

import com.abcinvestmentbank.generalledger.domain.Position;
import com.abcinvestmentbank.generalledger.domain.Transaction;
import com.abcinvestmentbank.generalledger.exception.TransactionFileException;
import com.abcinvestmentbank.generalledger.service.EODPositionCalculatorService;
import com.abcinvestmentbank.generalledger.service.PositionTransactionInputOutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.List;

@SpringBootApplication
public class GeneralledgerApplication implements CommandLineRunner {


    @Autowired
    private PositionTransactionInputOutputService positionTransactionInputOutputService;

    @Autowired
    private EODPositionCalculatorService EODPositionCalculatorService;

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(GeneralledgerApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

    }

    @Override
    public void run(String... args) throws TransactionFileException {

        //Step 1 : Read Start Day Positions from Source
        List<Position> positionsSODList = positionTransactionInputOutputService.readStartOfDayPositions(new File(
                "input_output_location/Input_StartOfDay_Positions.txt"));

        //Step 2 : Read Transactions from Source
        List<Transaction> transactionList = positionTransactionInputOutputService.readTransactions(new File(
                "input_output_location/1537277231233_Input_Transactions.txt"));

        //Step 3 : Process Transactions on Positions
        List<Position> positionEODList = EODPositionCalculatorService.calculatePositionsEOD(positionsSODList, transactionList);

        //Step 4 : output End of Day Positions with Delta to Destination file.
        positionTransactionInputOutputService.writePositionsEOD(positionEODList, new File(
                "input_output_location/Expected_EndOfDay_Positions.txt"));

    }
}
