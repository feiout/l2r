package com.l2r.entity;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;

/**
 * Created by messi on 2019-12-06.
 */
@Entity
@Table(name="hwcase_relation")
public class Hwcase_relation {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String cId;
    private String userId;
    private Date createDate;
    private String ownType; //1, modify ; 2, owner


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getOwnType() {
        return ownType;
    }

    public void setOwnType(String ownType) {
        this.ownType = ownType;
    }
}
