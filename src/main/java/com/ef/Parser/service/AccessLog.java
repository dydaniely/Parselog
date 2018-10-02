package com.ef.Parser.service;

import java.io.FileNotFoundException;

public interface AccessLog {

    String logParser(String fileName,String startDate, String duration, long threshold) throws FileNotFoundException;

    void printAndStoreThreatIp(String startDate, String duration, long threshold);
}
