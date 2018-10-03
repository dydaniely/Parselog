package com.ef.Parser.service;

import com.ef.Parser.dao.BaseDao;
import com.ef.Parser.dao.IpDto;
import com.ef.Parser.dao.ThreatDao;
import com.ef.Parser.domain.ThreatIP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
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
    private com.ef.Parser.dao.AccessLogDao AccessLogDao;
    @Autowired
    private BaseDao baseDao;
    @Autowired
    private ThreatDao threatDao;


    /**
     * Checks files under sourceReqPath and process based on delim
     * TODO processed file need to be in a different folder after processed
     *
     * @param sourceReqPath
     * @param startDate
     * @param duration
     * @param threshold
     * @return
     * @throws FileNotFoundException
     */
    @Override
    public String logParser(String sourceReqPath, String startDate, String duration, long threshold) throws FileNotFoundException {
        File folder = new File(sourceReqPath);
        File[] responseFiles = folder.listFiles();
      assert responseFiles != null;
        for (File completeFile : responseFiles) {
            if (completeFile.isFile()) {
                File file = Paths.get(completeFile.getPath()).toAbsolutePath().toFile();
                try {
                    Stream<String> stringStream = Files.lines(file.toPath());
                    List<String> rowLines = stringStream.collect(Collectors.toList());
                    parseToDatabase(rowLines);
                } catch (IOException e) {
                    System.out.println("error on reading file");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                printAndStoreThreatIp(startDate, duration, threshold);
            }
        }
        return new Date() + ":: Parsing Completed - " + responseFiles.length;
    }

    /**
     * Print and Store IP address attempts over threshold
     * DatatimeFormat is a bit different  for query purpose
     * @param startDate
     * @param duration
     * @param threshold
     */
    @Override
    public void printAndStoreThreatIp(String startDate, String duration, long threshold) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss");
        LocalDateTime dateTime=LocalDateTime.parse(startDate,timeFormatter);
        List<IpDto> accessLogs = baseDao.findByAccessDateAndThreshold(dateTime, duration, threshold);
        if (accessLogs.size() > 0) {
            List<ThreatIP> threatIPS = new ArrayList<>();
            System.out.println("Ip address attempted access to the web server over " + threshold + " times" + " " + duration);
            System.out.println("=====================================================");
            for (IpDto accessLog : accessLogs) {
                ThreatIP threatIP = new ThreatIP();
                threatIP.setComment("attempt  " + threshold + " times  in   " + duration);
                threatIP.setIp(accessLog.getIp());
                threatIP.setModOn(new Date());
                threatIPS.add(threatIP);
                System.out.println(accessLog.getIp());
            }
            threatDao.saveAll(threatIPS);
        }
    }

    private void parseToDatabase(List<String> rowLines) throws ParseException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println("Parsing Started , please wait ");
        List<com.ef.Parser.domain.AccessLog> accessLogs = new ArrayList<>();
        System.out.println("# parsing lines  " + rowLines.size());
        for (String s : rowLines) {
            com.ef.Parser.domain.AccessLog accessLog = new com.ef.Parser.domain.AccessLog();
            String[] x = s.split("\\|");
            accessLog.setAccessDate(LocalDateTime.parse(x[0], dateTimeFormatter));
            accessLog.setIp(x[1]);
            accessLog.setRequestType(x[2]);
            accessLog.setHttpStatus(x[3]);
            accessLog.setUserAgent(x[4]);
            accessLogs.add(accessLog);
        }
        System.out.println("Saving to DB");
        AccessLogDao.saveAll(accessLogs);

    }
}
