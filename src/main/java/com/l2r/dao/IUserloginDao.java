package com.l2r.dao;

import com.l2r.entity.Userlogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by messi on 2019-12-04.
 */
public interface IUserloginDao extends JpaRepository<Userlogin,String> {

    @Query(nativeQuery=true,value = "SELECT * from userlogin WHERE Password  = :password and LoginName = :loginName")
    Userlogin findbyUsernamePassword(@Param("password") String password,@Param("loginName") String loginName);
}
