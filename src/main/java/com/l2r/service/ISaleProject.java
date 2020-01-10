package com.l2r.service;

import com.l2r.base.IService;
import com.l2r.entity.SaleProject;

import java.util.List;

/**
 * Created by Messi on 2019/11/18.
 */


public interface ISaleProject extends IService<SaleProject> {
    List<SaleProject> findAll();
}
