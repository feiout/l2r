package com.l2r.dao;

import com.l2r.entity.Company_relation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by messi on 2019-12-20.
 */
@Repository
public interface ICompany_relationDao extends JpaRepository<Company_relation,String> {

    @Query(nativeQuery=true,value = "SELECT * from company_relation where subCompanyId= :companyId")
    List<Company_relation> FindListBySubCompayId(@Param("companyId") String companyId);

    @Query(nativeQuery=true,value = "SELECT * from company_relation where subCompanyId= :companyId")
    Company_relation FindbySubCompanyId(@Param("companyId") String companyId);

    @Query(nativeQuery=true,value = "SELECT * from company_relation where subCompanyId= :companyId And parentCompanyId=:parentId")
    Company_relation FindbyParentAndSubId(@Param("parentId") String parentId, @Param("companyId") String companyId);

}
