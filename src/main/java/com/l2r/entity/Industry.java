package com.l2r.entity;

import javax.persistence.*;

/**
 * Created by messi on 2019-12-23.
 */
@Entity
@Table(name="industry")
public class Industry {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;

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
}
