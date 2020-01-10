package com.l2r.service.Impl;

import com.l2r.base.AbstractService;
import com.l2r.dao.ISaleProjectDao;
import com.l2r.dao.IUserDao;
import com.l2r.dao.IUserloginDao;
import com.l2r.entity.SaleProject;
import com.l2r.entity.User;
import com.l2r.entity.User_login;
import com.l2r.service.ISaleProject;
import com.l2r.service.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Messi on 2019/11/18.
 */

@Service
public class ISaleProjectImpl extends AbstractService<SaleProject> implements ISaleProject {
    @Autowired
    private ISaleProjectDao saleProjectDao;
    @Override
    public JpaRepository<SaleProject, String> getRepository() {
        return saleProjectDao;
    }

    @Override
    public List<SaleProject> findAll() {
        List<SaleProject> saleProjects=saleProjectDao.findAll();
        return  saleProjects;
    }
}
