package com.log.Parser.dao;

import com.log.Parser.domain.LogFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author isddyt
 * 3/12/2019
 */
@org.springframework.stereotype.Repository
@Transactional
public interface LogFileDaoImpl extends Repository<LogFile,Long> {
    boolean existsByFileName(String name);

}

