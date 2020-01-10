package com.l2r.dao;

import com.l2r.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by messi on 2019-12-23.
 */
@Repository
public interface ICityDao extends JpaRepository<City,String>{

    @Query(nativeQuery=true,value="SELECT * from city")
    List<City> QueryALL();

    @Query(nativeQuery=true,value="SELECT * from city where provinceCode = :provinceCode")
    List<City> getCities(@Param("provinceCode") String provinceCode);
}
