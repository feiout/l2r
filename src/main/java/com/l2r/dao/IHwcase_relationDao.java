package com.l2r.dao;

import com.l2r.entity.Hwcase_relation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by messi on 2019-12-20.
 */
@Repository
public interface IHwcase_relationDao extends JpaRepository<Hwcase_relation,String> {

    @Query(nativeQuery=true,value = "SELECT  * from hwcase_relation where cId=:caseId and userId =:currentUserId and ownType=:ownType")
    Hwcase_relation FindByUserIdAndCIdAndAdmin(@Param("currentUserId") String currentUserId, @Param("caseId") String caseId, @Param("ownType") String ownType);

    @Query(nativeQuery=true,value = "SELECT  * from hwcase_relation where cId=:caseId and userId =:currentUserId")
    Hwcase_relation FindbyUserIdAndCid(@Param("currentUserId") String currentUserId, @Param("caseId") String caseId);
}
