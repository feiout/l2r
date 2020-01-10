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
        List<Industry> industries=cacheUtil.getIndustries();
        Result result=new Result(industries,false);
        System.out.println("GET Rest Call: /common/industrylist ...");
        return result;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/provincelist")
    public Result getProvinceList(HttpServletResponse response, @CookieValue(value = SecurityUtil.TOKEN,defaultValue = "") String token) {
        if (logger.isDebugEnabled()) {
            logger.debug("Rest Call: /common/provincelist ...");
        }
        List<Province> provinces=cacheUtil.getProvinces();
        Result result=new Result(provinces,false);
        System.out.println("GET Rest Call: /common/provincelist ...");
        return result;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/citylist/{provinceCode}")
    public Result getCityList(HttpServletResponse response, @PathVariable("provinceCode") String provinceCode, @CookieValue(value = SecurityUtil.TOKEN,defaultValue = "") String token) {
        if (logger.isDebugEnabled()) {
            logger.debug("Rest Call: /common/citylist/{provinceCode} ...");
        }
        List<City> cities=cacheUtil.getCities();
        List<City> filterCitis=new ArrayList<>();
        for(City c:cities){
            if (c.getProvinceCode().equals(provinceCode))
            {
                filterCitis.add(c);
            }
        }
        Result result=new Result(filterCitis);
        System.out.println("GET Rest Call: /common/citylist/{provinceCode} ...");
        return result;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/citylistbyprovincename/{provinceName}/{city}")
    public Result getCityListByProvinceName(HttpServletResponse response, @PathVariable("provinceName") String provinceName, @PathVariable("city") String city, @CookieValue(value = SecurityUtil.TOKEN,defaultValue = "") String token) {
        if (logger.isDebugEnabled()) {
            logger.debug("Rest Call: /common/citylistbyprovincename/{provinceName}/{city} ...");
        }
        List<City> cities=cacheUtil.getCities();
        List<City> filterCitis=new ArrayList<>();
        for(City c:cities){
            if (c.getProvinceName().equals(provinceName)&&c.getName().contains(city))
            {
                filterCitis.add(c);
            }
        }
        Result result=new Result(filterCitis);
        System.out.println("GET Rest Call: /common/citylistbyprovincename/{provinceName}/{city} ...");
        return result;
    }


    @RequestMapping(method = RequestMethod.GET,value = "/allcitylist")
    public Result getAllCityList(HttpServletResponse response,  @CookieValue(value = SecurityUtil.TOKEN,defaultValue = "") String token) {
        if (logger.isDebugEnabled()) {
            logger.debug("Rest Call: /common/allcitylist ...");
        }
        List<City> cities=cacheUtil.getCities();
        Result result=new Result(cities ,true);
        System.out.println("GET Rest Call: /common/allcitylist ...");
        return result;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/userlist")
    public Result getUserList(HttpServletResponse response, @CookieValue(value = SecurityUtil.TOKEN,defaultValue = "") String token) {
        if (logger.isDebugEnabled()) {
            logger.debug("Rest Call: /common/userlist ...");
        }
        List<User> users=cacheUtil.getUsers();
        Result result=new Result(users);
        System.out.println("GET Rest Call: /common/userlist ...");
        return result;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/companylist")
    public Result getCompanyList(HttpServletResponse response, @CookieValue(value = SecurityUtil.TOKEN,defaultValue = "") String token) {
        if (logger.isDebugEnabled()) {
            logger.debug("Rest Call: /common/companylist ...");
        }
        List<CompanyVo> companies=cacheUtil.getCompanys();
        Result result=new Result(companies);
        System.out.println("GET Rest Call: /common/companylist ...");
        return result;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/saleprojectlist")
    public Result getSaleProjectList(HttpServletResponse response, @CookieValue(value = SecurityUtil.TOKEN,defaultValue = "") String token) {
        if (logger.isDebugEnabled()) {
            logger.debug("Rest Call: /common/saleprojectlist ...");
        }
        List<SaleProject> saleProjects=cacheUtil.getSaleProjects();
        Result result=new Result(saleProjects);
        System.out.println("GET Rest Call: /common/saleprojectlist ...");
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
