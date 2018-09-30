package com.abcinvestmentbank.generalledger.service.impl;

import com.abcinvestmentbank.generalledger.service.FileHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Slf4j
@Service
public class FileHandlerServiceImpl implements FileHandlerService {
    @Override
    public List<String> readToStringList(File inputFile) {
        List<String> inputData = null;
        try {
            inputData = IOUtils.readLines(new FileInputStream(inputFile), "UTF-8");
        } catch (FileNotFoundException e) {
            log.error("File not found",e);
        } catch (IOException e) {
            log.error("IO exception",e);
        }
        return inputData;
    }

    @Override
    public void writeToFile(StringBuilder outputData, File outputFile) {
        try {
            IOUtils.write(outputData.toString(),new FileOutputStream(outputFile),"UTF-8");
        } catch (IOException e) {
            log.error("IO exception",e);
        }

    }
}
