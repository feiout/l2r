package com.l2r.dao;

import com.l2r.entity.User_login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by messi on 2019-12-04.
 */

@Repository
public interface IUserloginDao extends JpaRepository<User_login,String> {

    @Query(nativeQuery=true,value = "SELECT * from user_login WHERE Password  = :password and LoginName = :loginName")
    User_login findbyUsernamePassword(@Param("password") String password,@Param("loginName") String loginName);
}
