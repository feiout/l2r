package com.l2r.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Messi on 2019/11/18.
 */

@Entity
@Table(name="userdepartment")
public class Userdepartment {
    @Id
    private Integer Id;
    private String Department;
    private Integer SubscriptionId;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public Integer getSubscriptionId() {
        return SubscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        SubscriptionId = subscriptionId;
    }
}
