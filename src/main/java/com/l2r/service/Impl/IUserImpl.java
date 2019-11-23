package com.l2r.service.Impl;

import com.l2r.base.AbstractService;
import com.l2r.dao.IUserDao;
import com.l2r.entity.User;
import com.l2r.service.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Messi on 2019/11/18.
 */

@Service
public class IUserImpl extends AbstractService<User> implements IUser {
    @Autowired
    private IUserDao userDao;

    public JpaRepository<User,String> getRepository(){return userDao;}

    @Override
    public List<User> FindAll() {
        List<User> users=userDao.findAll();
        return users;
    }

    @Override
    public List<User> QueryALL() {
        List<User> users=userDao.QueryALL();
        return users;
    }

    @Override
    public User QueryUserById(Integer i) {
        User user=userDao.findById(i);
        return user;
    }

}
