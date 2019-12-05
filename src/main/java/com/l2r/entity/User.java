package com.l2r.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Created by Messi on 2019/11/18.
 */

@Entity
@Table(name="user")
public class User implements Serializable {
    @Id
    private Integer id;
    private String name;
    private String department;
    private String userType;
    private Integer subscriptionId;
    @Transient
    private Userlogin userlogin;
    @Transient
    private String status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Userlogin getUserlogin() {
        return userlogin;
    }

    public void setUserlogin(Userlogin userlogin) {
        this.userlogin = userlogin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
