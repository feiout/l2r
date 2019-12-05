package com.l2r.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by Messi on 2019/11/18.
 */

@Entity
@Table(name="userlogin")
public class Userlogin {
    @Id
    private Integer id;
    private Integer userId;
    private String loginName;
    private String password;

    public Userlogin(String loginName, String password) {
        this.loginName = loginName;
        this.password = password;
    }

    public Userlogin() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
