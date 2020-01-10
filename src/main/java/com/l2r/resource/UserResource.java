package com.l2r.resource;

import com.l2r.base.BaseResource;
import com.l2r.base.Result;
import com.l2r.dao.IUser_logsDao;
import com.l2r.entity.*;
import com.l2r.utils.CacheUtil;
import com.l2r.utils.SecurityUtil;
import com.l2r.utils.WithoutAuthentication;
import com.l2r.service.IUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

/**
 * Created by Messi on 2019/11/18.
 */
@RestController
@RequestMapping("/user")

public class UserResource extends BaseResource {
private final Logger logger = LoggerFactory.getLogger(this.getClass());
@Autowired
private IUser userService;
@Autowired
private SecurityUtil securityService;
@Autowired
private CacheUtil cacheUtil;
@Autowired
private IUser_logsDao userLogsDao;

/**
 * @param user
 * @param response
 * @return
 * @throws NoSuchAlgorithmException
 * @throws IOException
 */
@RequestMapping(method = RequestMethod.POST,value = "/authentication")
@WithoutAuthentication
public Result LoginDes(@RequestBody User user, HttpServletResponse response) throws NoSuchAlgorithmException, IOException {
    if(logger.isDebugEnabled()){
        logger.debug("Rest Call: /user/authentication ...");
    }
    Result result = null;
    //数据完整性检测
    if (user == null || StringUtils.isEmpty(user.getUserlogin().getLoginName()) || StringUtils.isEmpty(user.getUserlogin().getPassword())) {
        return null;
    }
    User_login ul = new User_login(user.getUserlogin().getLoginName(),user.getUserlogin().getPassword());
    User dbuser=userService.LoginForSingleUser(ul);
    if (dbuser != null) {
        String token = SecurityUtil.generateToken(user.getUserlogin().getLoginName());
        Cookie cookie = new Cookie(SecurityUtil.TOKEN, token);
        cookie.setPath("/");
        response.addCookie(cookie);
        result = new Result(dbuser,true);
        result.setToken(token);
        securityService.SaveLoginedUser(token,dbuser);
        User_logs ulog=new User_logs(ul.getLoginName(),new Date());
        userLogsDao.save(ulog);
    }
    System.out.println("GET Rest Call: /user/authentication ...");
    return result;
}


@RequestMapping(method = RequestMethod.GET,value = "/list")
public Result getUserList(HttpServletResponse response,@CookieValue(value = SecurityUtil.TOKEN,defaultValue = "") String token){
    if(logger.isDebugEnabled()){
        logger.debug("Rest Call: /user/list ...");
    }
    List <User> users=userService.QueryALL();
    Result result=new Result(users);
    System.out.println("GET Rest Call: /user/list ...");
    return result;
}

@RequestMapping(method = RequestMethod.GET,value = "/id/{userId}")
public Result queryUserById(HttpServletResponse response, @PathVariable("userId") Integer userId,
                            @CookieValue(value = SecurityUtil.TOKEN, defaultValue = "") String token){
    if(logger.isDebugEnabled()){
        logger.debug("Rest Call: /user/id/{userId} ...");
    }
    User users=userService.QueryUserById(userId);
    users.setStatus(token);
    Result result=new Result(users);
    System.out.println("GET Rest Call: /user/id/{userId} ...");
    return result;
}

}
