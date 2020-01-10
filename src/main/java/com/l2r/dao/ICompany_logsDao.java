package com.l2r.dao;

import com.l2r.entity.Company_logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by messi on 2019-12-20.
 */

@Repository
public interface ICompany_logsDao extends JpaRepository<Company_logs,String> {

    @Query(nativeQuery=true,value = "SELECT * from company_logs where companyId =:companyId ORDER BY id DESC LIMIT 1")
    Company_logs FindByCompanyId(@Param("companyId") String companyId);
}
