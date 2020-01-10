package com.l2r.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Messi on 2019/11/18.
 */

@Entity
@Table(name="saleProject")
public class SaleProject {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String saleProjectId;
    private String name;
    private String note;
    private String responsible;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSaleProjectId() {
        return saleProjectId;
    }

    public void setSaleProjectId(String saleProjectId) {
        this.saleProjectId = saleProjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }
}
