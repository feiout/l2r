package com.l2r.service.Impl;

import com.l2r.base.AbstractService;
import com.l2r.dao.IUserDao;
import com.l2r.dao.IUserloginDao;
import com.l2r.entity.User;
import com.l2r.entity.User_login;
import com.l2r.service.IUser;
import com.l2r.service.IUserlogin;
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
    @Autowired
    private IUserloginDao userloginDao;

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

    @Override
    public User LoginForSingleUser(User_login ul) {
        User_login dbul=userloginDao.findbyUsernamePassword(ul.getPassword(),ul.getLoginName());
        User user=new User();
        if(null != dbul){
            user=userDao.findById(dbul.getId());
        }
        return user;
    }

}
