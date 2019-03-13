package com.log.Parser.dao;

import com.log.Parser.domain.SourceIp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceIpDao extends CrudRepository<SourceIp,Long> {
}
