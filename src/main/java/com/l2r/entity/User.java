package com.l2r.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Messi on 2019/11/18.
 */

@Entity
@Table(name="User")
public class User {
    @Id
    private String Id;
    private String Name;
    private String Department;
    private String UserType;
    private Date LastLogin;
    private Date LastChange;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public Date getLastLogin() {
        return LastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        LastLogin = lastLogin;
    }

    public Date getLastChange() {
        return LastChange;
    }

    public void setLastChange(Date lastChange) {
        LastChange = lastChange;
    }

}
