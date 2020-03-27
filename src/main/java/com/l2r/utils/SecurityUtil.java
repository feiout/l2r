package com.l2r.utils;

import com.l2r.entity.*;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by messi on 2019-12-05.  缓存的bean名称为"Dict"
 */

@Service
@SuppressWarnings("restriction")
public class SecurityUtil {

    public static final String TOKEN = "token";

    public void Init(){
        InitLoginUser();
    }

   private void  InitLoginUser(){
       Map<String,User> loginedUserMap=new HashMap<>();
       Cache cache = SpringContextUtil.getBean("Dict",Cache.class);
       Element element = new Element("loginedUser", loginedUserMap);
       cache.put(element);
   }

//    public User getLoginedUer(String name){
//        Cache cache = SpringContextUtil.getBean("User",Cache.class);
//        Map<String,User> logindeMap=(Map<String,User>)cache.get("loginedUser");
//        return logindeMap.get(name);
//    }

    public void SaveLoginedUser(String token,User user){
        Cache cache = SpringContextUtil.getBean("Dict",Cache.class);
        Map<String,User> logindeMap=(Map<String,User>)cache.get("loginedUser").getObjectValue();
        if(logindeMap.get(token)==null){
            logindeMap.put(token,user);
        }
    }

    public static String generateToken(String key) throws NoSuchAlgorithmException {
        StringBuffer input = new StringBuffer(key);
        input.append(System.currentTimeMillis());
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytes = md.digest(input.toString().getBytes());
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);
    }

    public static User getLoginEmployee(String token) {
        Cache cache = SpringContextUtil.getBean("Dict",Cache.class);
        Map<String,User> logindeMap=(Map<String,User>)cache.get("loginedUser").getObjectValue();
        return logindeMap.get(token);
    }



}
