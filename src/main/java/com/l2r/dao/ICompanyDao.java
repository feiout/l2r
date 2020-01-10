package com.l2r.dao;

import com.l2r.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by messi on 2019-12-20.
 */
@Repository
public interface ICompanyDao extends JpaRepository<Company,String>{

    @Query(nativeQuery=true,value = "SELECT * from company where id= :id")
    Company FindById(@Param("id") Integer id);

    @Query(nativeQuery=true,value = "SELECT * from company where name= :name")
    List<Company> findByName(@Param("name") String name);

    @Query(nativeQuery=true,value = "SELECT * from company where name= :name and id= :id")
    List<Company> findByNameAndId(@Param("name") String name, @Param("id") Integer id);

    @Query(nativeQuery=true,value = "SELECT companyId as id,name from company")
    List<Object[]> FindAllByVos();

    @Query(nativeQuery=true,value = "SELECT * from company where companyId= :companyId")
    Company FindByCompanyId(@Param("companyId") String companyId);

    @Query(nativeQuery=true,value = "SELECT * from company where companyId in (SELECT parentCompanyId from company_relation where subCompanyId =:companyId)")
    Company FindParentByCompanyId(@Param("companyId") String companyId);

    @Query(nativeQuery=true,value = "SELECT companyId as id,name from company where upper(name) LIKE CONCAT('%',:name,'%') limit 5")
    List<Object[]> FindAllVosByNameLike(@Param("name") String name);
}
