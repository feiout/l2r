package com.l2r.service;

import com.l2r.base.IService;
import com.l2r.entity.User;

import java.util.List;

/**
 * Created by Messi on 2019/11/18.
 */


public interface IUser extends IService<User> {
    List<User> FindAll();
    List<User> QueryALL();
    User QueryUserById(Integer i);
}
