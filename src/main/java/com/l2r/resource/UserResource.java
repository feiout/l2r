package com.l2r.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;

/**
 * Created by Messi on 2019/11/18.
 */
@RestController
@RequestMapping("/user")

public class UserResource {
    @RequestMapping(method = RequestMethod.GET,value = "/list")
    public String hello(){
        return "Hello List!";
    }

    @RequestMapping(method = RequestMethod.GET,value = "/pop")
    public String hello2(){
        return "Hello pop!";
    }
}
