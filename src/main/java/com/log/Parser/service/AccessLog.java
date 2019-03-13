package com.log.Parser.service;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface AccessLog {

    String logParser(String fileName) throws IOException;

    void printAndStoreThreatIp(String startDate, String duration, long threshold);
}
