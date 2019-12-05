package com.l2r.resource;

import com.l2r.base.BaseResource;
import com.l2r.base.Result;
import com.l2r.utils.SecurityUtil;
import com.l2r.utils.WithoutAuthentication;
import com.l2r.entity.User;
import com.l2r.entity.Userlogin;
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

//@Autowired
//private IUserlogin userloginService;

    @RequestMapping(method = RequestMethod.GET,value = "/id")
    public Result getUserList(HttpServletResponse response){
        List <User> users=userService.FindAll();
        Result result=new Result(users);
        return result;
    }

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
            logger.debug("Rest Call: /authentication ...");
        }
        Result result = null;
        //数据完整性检测
        if (user == null || StringUtils.isEmpty(user.getUserlogin().getLoginName()) || StringUtils.isEmpty(user.getUserlogin().getPassword())) {
            return null;
        }
        Userlogin ul = new Userlogin(user.getUserlogin().getLoginName(),user.getUserlogin().getPassword());
        User dbuser=userService.LoginForSingleUser(ul);
        if (dbuser != null) {
            String token = SecurityUtil.generateToken(user.getUserlogin().getLoginName());
            Cookie cookie = new Cookie(SecurityUtil.TOKEN, token);
            cookie.setPath("/");
            response.addCookie(cookie);
            result = new Result(dbuser,true);
            result.setToken(token);
            securityService.SaveLoginedUser(token,dbuser);
//            SecurityUtil.putLoginEmployee(token, dbuser);
        }
        return result;
    }


    @RequestMapping(method = RequestMethod.GET,value = "/list")
    public Result queryUserById(HttpServletResponse response,
                                @CookieValue(value = SecurityUtil.TOKEN, defaultValue = "") String token){
        User users=userService.QueryUserById(1);
        users.setStatus(token);
        Result result=new Result(users);
        return result;
    }

}
