package com.log.Parser.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Repository
@Transactional
public interface BaseDao {

    public List<IpDto> findByAccessDateAndThreshold(LocalDateTime date, String duration, long l);


}
