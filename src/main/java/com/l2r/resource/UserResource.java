package com.l2r.resource;

import com.l2r.base.BaseResource;
import com.l2r.base.Result;
import com.l2r.entity.User;
import com.l2r.service.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * Created by Messi on 2019/11/18.
 */
@RestController
@RequestMapping("/user")

public class UserResource extends BaseResource {
@Autowired
private IUser userService;

    @RequestMapping(method = RequestMethod.GET,value = "/list")
    public Result getUserList(HttpServletResponse response){
        List <User> users=userService.FindAll();
        Result result=new Result(users);
        return result;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/query")
    public Result queryUserList(HttpServletResponse response){
        List <User> users=userService.QueryALL();
        Result result=new Result(users);
        return result;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/id")
    public Result queryUserById(HttpServletResponse response){
        User users=userService.QueryUserById(1);
        Result result=new Result(users);
        return result;
    }

}
