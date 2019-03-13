package com.log.Parser.domain;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class SourceIp
{
    @Id
    @GeneratedValue
    private long Id;
    private String Ip;
    private String Comment;
    private Date modOn;
    public long getId() {
        return Id;
    }
    public void setId(long id) {
        Id = id;
    }
    public String getIp() {
        return Ip;
    }
    public void setIp(String ip) {
        Ip = ip;
    }
    public String getComment() {
        return Comment;
    }
    public void setComment(String comment) {
        Comment = comment;
    }
    public Date getModOn() {
        return modOn;
    }
    public void setModOn(Date modOn) {
        this.modOn = modOn;
    }
}
