package com.l2r.dao;

import com.l2r.entity.Hwcase_logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by messi on 2019-12-20.
 */
@Repository
public interface IHwcase_logsDao extends JpaRepository<Hwcase_logs,String> {
    @Query(nativeQuery=true,value = "SELECT  * from hwcase_logs where cid=:cId order by ID desc LIMIT 1")
    Hwcase_logs FindyByCid(@Param("cId") Integer id);
}
