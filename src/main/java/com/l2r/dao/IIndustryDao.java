package com.l2r.dao;

import com.l2r.entity.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by messi on 2019-12-23.
 */
@Repository
public interface IIndustryDao extends JpaRepository<Industry,String>{

    @Query(nativeQuery=true,value="SELECT * from industry")
    List<Industry> QueryALL();
}
