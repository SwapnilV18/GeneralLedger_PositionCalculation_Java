package com.abcinvestmentbank.generalledger.exception;

import java.io.IOException;

public class TransactionFileException extends IOException{

    public TransactionFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
