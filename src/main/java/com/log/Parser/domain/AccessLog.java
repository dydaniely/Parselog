package com.log.Parser.domain;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class AccessLog {
    @Id
    @GeneratedValue
    private long id;
    private String ip;
    private LocalDateTime accessDate;
    private String requestType;
    private String httpStatus;
    private String userAgent;
    private String status;
    private LocalTime responseTime;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "logFileId")
    private LogFile logFile;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public LocalDateTime getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(LocalDateTime accessDate) {
        this.accessDate = accessDate;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalTime getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(LocalTime responseTime) {
        this.responseTime = responseTime;
    }

    public LogFile getLogFile() {
        return logFile;
    }

    public void setLogFile(LogFile logFile) {
        this.logFile = logFile;
    }
}
