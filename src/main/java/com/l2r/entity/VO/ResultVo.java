package com.l2r.entity.VO;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by messi on 2019-12-06.
 */

public class ResultVo {

    private Integer id;
    private String ex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

}
