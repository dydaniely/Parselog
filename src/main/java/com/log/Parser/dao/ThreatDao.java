package com.log.Parser.dao;

import com.log.Parser.domain.ThreatIP;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreatDao extends CrudRepository<ThreatIP,Long> {
}
