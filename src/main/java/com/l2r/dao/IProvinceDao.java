package com.l2r.dao;

import com.l2r.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by messi on 2019-12-23.
 */
@Repository
public interface IProvinceDao extends JpaRepository<Province,String> {

    @Query(nativeQuery=true,value="SELECT * from province")
    List<Province> QueryALL();

    @Query(nativeQuery=true,value="SELECT * from province where name =:provinceName")
    Province FindByProvinceName(@Param("provinceName") String provinceName);
}
