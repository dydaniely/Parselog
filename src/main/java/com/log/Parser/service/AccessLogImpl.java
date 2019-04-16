package com.log.Parser.service;

import com.log.Parser.dao.BaseDao;
import com.log.Parser.dao.IpDto;
import com.log.Parser.dao.LogFileDaoImpl;
import com.log.Parser.dao.SourceIpDao;
import com.log.Parser.domain.LogFile;
import com.log.Parser.domain.SourceIp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AccessLogImpl implements AccessLog {
    @Autowired
    private com.log.Parser.dao.AccessLogDao AccessLogDao;


    @Autowired
    private BaseDao baseDao;

    @Autowired
    private LogFileDaoImpl logFileDao;
    @Autowired
    private SourceIpDao sourceIpDao;

    /**
     * Checks files under sourceReqPath and process based on delim
     * TODO processed file need to be in a different folder after processed
     *
     * @param sourceReqPath
     * @return
     * @throws FileNotFoundException
     */
    @Override
    public String logParser(String sourceReqPath) throws IOException {
        File folder = new File(sourceReqPath);
        File[] responseLogFiles = folder.listFiles();
        assert responseLogFiles != null;
        for (File completeLogFile : responseLogFiles) {
            if (completeLogFile.isFile()) {
                if (!logFileDao.existsByFileName(completeLogFile.getName())) {
                    LogFile logFile = logFileWrapper(completeLogFile);
                    File file = Paths.get(completeLogFile.getPath()).toAbsolutePath().toFile();
                    try {
                        Stream<String> stringStream = Files.lines(file.toPath());
                        List<String> rowLines = stringStream.collect(Collectors.toList());
                        parseToDatabase(rowLines, logFile);
                    } catch (IOException e) {
                        System.out.println("error on reading file");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
//                printAndStoreThreatIp(startDate, duration, threshold);
            }
        }
        return LocalDate.now().toString() + "- Parsing Completed - " + responseLogFiles.length;
    }

    private LogFile logFileWrapper(File completLogile) throws IOException {

        Path file = completLogile.toPath();
        BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);

        LocalDateTime creationDate = LocalDateTime.parse(attrs.creationTime().toString(), DateTimeFormatter.ISO_DATE_TIME);
        LogFile logFile = new LogFile();
        logFile.setFileName(completLogile.getName());
        logFile.setCreatedDate(creationDate);
        logFile.setModOn(LocalDate.now());
        return logFile;
    }

    /**
     * Print and Store IP address attempts over threshold
     * Datatimlogormat is a bit different  for query purpose
     *
     * @param startDate
     * @param duration
     * @param threshold
     */
    @Override
    public void printAndStoreThreatIp(String startDate, String duration, long threshold) {
        DateTimeFormatter timlogormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(startDate, timlogormatter);
        List<IpDto> accessLogs = baseDao.findByAccessDateAndThreshold(dateTime, duration, threshold);
        if (accessLogs.size() > 0) {
            List<SourceIp> sourceIps = new ArrayList<>();
            System.out.println("Ip address attempted access to the web server over " + threshold + " times" + " " + duration);
            System.out.println("=====================================================");
            for (IpDto accessLog : accessLogs) {
                SourceIp sourceIp = new SourceIp();
                sourceIp.setComment("attempt  " + threshold + " times  in   " + duration);
                sourceIp.setIp(accessLog.getIp());
                sourceIp.setModOn(new Date());
                sourceIps.add(sourceIp);
                System.out.println(accessLog.getIp());
            }
            sourceIpDao.saveAll(sourceIps);
        }
    }

    private void parseToDatabase(List<String> rowLines, LogFile logFile) throws ParseException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss");
        System.out.println("Parsing Started , please wait ");
        List<com.log.Parser.domain.AccessLog> accessLogs = new ArrayList<>();
        System.out.println("# Parsing lines  " + rowLines.size());
        for (String s : rowLines) {
            com.log.Parser.domain.AccessLog accessLog = new com.log.Parser.domain.AccessLog();
            String[] x = s.split("\\s+");
            String dateValue = x[3].replace("[", "");
            LocalDateTime localDateTime=LocalDateTime.parse(dateValue, dateTimeFormatter);
            accessLog.setAccessDate(localDateTime);
            accessLog.setIp(x[0]);
            accessLog.setResponseTime(localDateTime.toLocalTime());
            accessLog.setRequestType(x[5].replaceAll("\"",""));
            accessLog.setHttpStatus(x[8]);
            accessLog.setStatus(x[9]);
            accessLog.setUserAgent(x[6]);
            accessLog.setLogFile(logFile);
            accessLogs.add(accessLog);
        }
        System.out.println("Saving to DB");
        AccessLogDao.saveAll(accessLogs);
        System.out.println(rowLines.size() + " Saving to DB");
    }
}
