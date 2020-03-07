package com.l2r.resource;

import com.l2r.base.BaseResource;
import com.l2r.base.Result;
import com.l2r.dao.ICityDao;
import com.l2r.dao.IIndustryDao;
import com.l2r.dao.IProvinceDao;
import com.l2r.dao.IUserDao;
import com.l2r.entity.*;
import com.l2r.entity.VO.CompanyVo;
import com.l2r.service.IUser;
import com.l2r.utils.CacheUtil;
import com.l2r.utils.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by messi on 2019-12-24.
 */

@RestController
@RequestMapping("/common")
public class CommonResource extends BaseResource {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private IProvinceDao provinceDao;
    @Autowired
    private ICityDao cityDao;
    @Autowired
    private IIndustryDao industryDao;
    @Autowired
    private IUserDao userDao;

    @RequestMapping(method = RequestMethod.GET,value = "/industrylist")
    public Result getIndustryList(HttpServletResponse response, @CookieValue(value = SecurityUtil.TOKEN,defaultValue = "") String token) {
        if (logger.isDebugEnabled()) {
            logger.debug("Rest Call: /common/industrylist ...");
        }
        long t1 = new Date().getTime();
        List<Industry> industries=cacheUtil.getIndustries();
        Result result=new Result(industries,false);
        long t2 = new Date().getTime();
        System.out.println("GET Rest Call: /common/industrylist ..."+(t2-t1));
        return result;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/provincelist")
    public Result getProvinceList(HttpServletResponse response, @CookieValue(value = SecurityUtil.TOKEN,defaultValue = "") String token) {
        if (logger.isDebugEnabled()) {
            logger.debug("Rest Call: /common/provincelist ...");
        }
        long t1 = new Date().getTime();
        List<Province> provinces=cacheUtil.getProvinces();
        Result result=new Result(provinces,false);
        long t2 = new Date().getTime();
        System.out.println("GET Rest Call: /common/provincelist ..."+(t2-t1));
        return result;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/citylist/{provinceCode}")
    public Result getCityList(HttpServletResponse response, @PathVariable("provinceCode") String provinceCode, @CookieValue(value = SecurityUtil.TOKEN,defaultValue = "") String token) {
        if (logger.isDebugEnabled()) {
            logger.debug("Rest Call: /common/citylist/{provinceCode} ...");
        }
        long t1 = new Date().getTime();
        List<City> cities=cacheUtil.getCities();
        List<City> filterCitis=new ArrayList<>();
        for(City c:cities){
            if (c.getProvinceCode().equals(provinceCode))
            {
                filterCitis.add(c);
            }
        }
        Result result=new Result(filterCitis);
        long t2 = new Date().getTime();
        System.out.println("GET Rest Call: /common/citylist/{provinceCode} ..."+(t2-t1));
        return result;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/citylistbyprovincename/{provinceName}/{city}")
    public Result getCityListByProvinceName(HttpServletResponse response, @PathVariable("provinceName") String provinceName, @PathVariable("city") String city, @CookieValue(value = SecurityUtil.TOKEN,defaultValue = "") String token) {
        if (logger.isDebugEnabled()) {
            logger.debug("Rest Call: /common/citylistbyprovincename/{provinceName}/{city} ...");
        }
        long t1 = new Date().getTime();
        List<City> cities=cacheUtil.getCities();
        List<City> filterCitis=new ArrayList<>();
        for(City c:cities){
            if (c.getProvinceName().equals(provinceName)&&c.getName().contains(city))
            {
                filterCitis.add(c);
            }
        }
        Result result=new Result(filterCitis);
        long t2 = new Date().getTime();
        System.out.println("GET Rest Call: /common/citylistbyprovincename/{provinceName}/{city} ..."+(t2-t1));
        return result;
    }


    @RequestMapping(method = RequestMethod.GET,value = "/allcitylist")
    public Result getAllCityList(HttpServletResponse response,  @CookieValue(value = SecurityUtil.TOKEN,defaultValue = "") String token) {
        if (logger.isDebugEnabled()) {
            logger.debug("Rest Call: /common/allcitylist ...");
        }
        long t1 = new Date().getTime();
        List<City> cities=cacheUtil.getCities();
        Result result=new Result(cities ,true);
        long t2 = new Date().getTime();
        System.out.println("GET Rest Call: /common/allcitylist ..."+(t2-t1));
        return result;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/userlist")
    public Result getUserList(HttpServletResponse response, @CookieValue(value = SecurityUtil.TOKEN,defaultValue = "") String token) {
        if (logger.isDebugEnabled()) {
            logger.debug("Rest Call: /common/userlist ...");
        }
        long t1 = new Date().getTime();
        List<User> users=cacheUtil.getUsers();
        Result result=new Result(users);
        long t2 = new Date().getTime();
        System.out.println("GET Rest Call: /common/userlist ..."+(t2-t1));
        return result;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/companylist")
    public Result getCompanyList(HttpServletResponse response, @CookieValue(value = SecurityUtil.TOKEN,defaultValue = "") String token) {
        if (logger.isDebugEnabled()) {
            logger.debug("Rest Call: /common/companylist ...");
        }
        long t1 = new Date().getTime();
        List<CompanyVo> companies=cacheUtil.getCompanys();
        Result result=new Result(companies);
        long t2 = new Date().getTime();
        System.out.println("GET Rest Call: /common/companylist ..."+(t2-t1));
        return result;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/saleprojectlist")
    public Result getSaleProjectList(HttpServletResponse response, @CookieValue(value = SecurityUtil.TOKEN,defaultValue = "") String token) {
        if (logger.isDebugEnabled()) {
            logger.debug("Rest Call: /common/saleprojectlist ...");
        }
        long t1 = new Date().getTime();
        List<SaleProject> saleProjects=cacheUtil.getSaleProjects();
        Result result=new Result(saleProjects);
        long t2 = new Date().getTime();
        System.out.println("GET Rest Call: /common/saleprojectlist ..."+(t2-t1));
        return result;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/productlist")
    public Result getProductList(HttpServletResponse response, @CookieValue(value = SecurityUtil.TOKEN,defaultValue = "") String token) {
        if (logger.isDebugEnabled()) {
            logger.debug("Rest Call: /common/productlist ...");
        }
        long t1 = new Date().getTime();
        List<Product> products=cacheUtil.getProducts();
        Result result=new Result(products);
        long t2 = new Date().getTime();
        System.out.println("GET Rest Call: /common/productlist ..."+(t2-t1));
        return result;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/filterproductlist/{product}")
    public Result getProductFilterList(HttpServletResponse response, @PathVariable("product") String product, @CookieValue(value = SecurityUtil.TOKEN,defaultValue = "") String token) {
        if (logger.isDebugEnabled()) {
            logger.debug("Rest Call: /common/filterproductlist/{product} ...");
        }
        long t1 = new Date().getTime();
        List<Product> products=cacheUtil.getProducts();
        List<Product> filterProducts=new ArrayList<>();
        for(Product p:products){
            if (p.getName().toUpperCase().contains(product.toUpperCase()))
            {
                filterProducts.add(p);
            }
        }
        Result result=new Result(filterProducts);
        long t2 = new Date().getTime();
        System.out.println("GET Rest Call: /common/filterproductlist/{product} ..."+(t2-t1));
        return result;
    }

}
