package com.l2r.dao;

import com.l2r.entity.Hwcase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by messi on 2019-12-20.
 */
@Repository
public interface IHwcaseDao extends JpaRepository<Hwcase,String> {

    @Query(nativeQuery=true,value = "SELECT id,caseId,type,source,pendingDate,companyId,companyName,province,city,address,customerId,customerName,title,email,cellphone,workphone \n" +
            "from hwcase where customerId= :customerId")
    List<Hwcase> findListByCustomerId(@Param("customerId") Integer customerId);

    @Query(nativeQuery=true,value = "SELECT id,caseId,type,source,pendingDate,companyId,companyName,province,city,address,customerId,customerName,title,email,cellphone,workphone \n" +
            "from hwcase where companyId= :companyId")
    List<Hwcase> findListByCompanyId(@Param("companyId") Integer companyId);

    @Query(nativeQuery=true,value = "SELECT * from hwcase where id= :cId")
    Hwcase findByCaseId(@Param("cId") Integer cId);

}
