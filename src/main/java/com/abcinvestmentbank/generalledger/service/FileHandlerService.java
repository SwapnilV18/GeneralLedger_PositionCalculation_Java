package com.abcinvestmentbank.generalledger.service;

import java.io.File;
import java.util.List;

public interface FileHandlerService {

    List<String> readToStringList(File inputFile);

    void writeToFile(StringBuilder outputData, File outputFile);
}
