package com.ef.Parser.dao;

import com.ef.Parser.domain.Period;
import org.hibernate.Session;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class BaseDaoImpl implements BaseDao {

    @Autowired
    EntityManager entityManager;


   private static final String sql = "Select  ip, count(1) as val  from AccessLog where accessDate between :sDate and :eDate \n" +
            "group by ip having count(1)  > :threshold";

    @Override
    public List<IpDto> findByAccessDateAndThreshold(LocalDateTime date, String duration, long l) {
        LocalDateTime endDate = null, startDate = null;
        if (duration.equals(Period.daily)) {
            endDate = date.plusDays(1);
            startDate = date;
        } else if (duration.equals(Period.hourly)) {
            endDate = date.plusHours(1);
            startDate = date;
        }
        Session session = entityManager.unwrap(Session.class);
        List<IpDto> accessLogs = session.createNativeQuery(sql)
                .addScalar("ip", StandardBasicTypes.STRING)
                .addScalar("val", StandardBasicTypes.LONG)
                .setParameter("eDate", endDate)
                .setParameter("sDate", startDate)
                .setParameter("threshold", l).
                        setResultTransformer(new AliasToBeanResultTransformer(IpDto.class)).list();

        return accessLogs;
    }


}
