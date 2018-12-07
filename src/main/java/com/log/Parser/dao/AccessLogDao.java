package com.log.Parser.dao;

import com.log.Parser.domain.AccessLog;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AccessLogDao extends CrudRepository<AccessLog,Long> {


//   public List<AccessLog> findByAccessDateAndThreshold(LocalDateTime date, long l) ;


}
