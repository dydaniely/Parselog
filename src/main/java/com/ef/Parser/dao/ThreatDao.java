package com.ef.Parser.dao;

import com.ef.Parser.domain.ThreatIP;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreatDao extends CrudRepository<ThreatIP,Long> {
}
