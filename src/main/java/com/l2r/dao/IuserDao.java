package com.l2r.dao;

import com.l2r.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Messi on 2019/11/18.
 */

@Repository
public interface IUserDao extends JpaRepository<User,String> {
    @Query(value="Select * FROM User", nativeQuery=true)
    List<User> QueryALL();

    User findById(Integer i);
}
