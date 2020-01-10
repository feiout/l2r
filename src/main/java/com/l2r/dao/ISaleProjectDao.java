package com.l2r.dao;

import com.l2r.entity.SaleProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Messi on 2019/11/18.
 */

@Repository
public interface ISaleProjectDao extends JpaRepository<SaleProject,String> {
    @Query(value="Select * FROM SaleProject", nativeQuery=true)
    List<SaleProject> QueryALL();
}
