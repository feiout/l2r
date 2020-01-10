package com.l2r.resource;

import com.l2r.base.BaseResource;
import com.l2r.base.Result;
import com.l2r.entity.*;
import com.l2r.entity.VO.CompanyVo;
import com.l2r.service.ICompany;
import com.l2r.service.ICustomer;
import com.l2r.service.ISaleProject;
import com.l2r.utils.CacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by messi on 2019-12-20.
 */
@RestController
@RequestMapping("/company")
public class CompanyResource extends BaseResource {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ICompany iCompany;
    @Autowired
    private ICustomer iCustomer;
    @Autowired
    private CacheUtil cacheUtil;


    @RequestMapping(method = RequestMethod.GET,value = "/company/{companyId}")
    public Result getCompany(HttpServletResponse response, @PathVariable("companyId") String companyId) {
        if(logger.isDebugEnabled()){
            logger.debug("Rest Call: /company/company/{companyId} ...");
        }
        Company company =iCompany.FindByComapnyId(companyId);
        List<Customer> customers=iCustomer.FindByCompanyId(companyId);
        Company_logs logs=iCompany.FindLogsByCompanyId(companyId);
        Company parentCompany=iCompany.FindCrByCompanyId(companyId);
        company.setCustomerList(customers);
        company.setNote(logs.getNote());
        if(null!=parentCompany) {
            company.setParentId(parentCompany.getCompanyId());
            company.setParentName(parentCompany.getName());
        }
        Result result=new Result(company,false);
        System.out.println("GET Rest Call: /company/company/{companyId} ...");
        return result;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/namelike/{name}")
    public Result getCompanyListByNameLike(HttpServletResponse response, @PathVariable("name") String name) {
        if(logger.isDebugEnabled()){
            logger.debug("Rest Call: /company/namelike/{name} ...");
        }
        long t1 = new Date().getTime();
        List<CompanyVo> companyVos=new ArrayList<>();
        List<CompanyVo> cacheCompanyVos =cacheUtil.getCompanys();
        int i=0;
        for(CompanyVo c:cacheCompanyVos)
        {
            if(c.getName().toUpperCase().contains(name.toUpperCase()))
            {
                companyVos.add(c);
                i++;
            }
            if(i>10) break;
        }
        Result result=new Result(companyVos,false);
        long t2 = new Date().getTime();
        System.out.println("GET Rest Call: /company/namelike/{name} ..."+(t2-t1));
        return result;
    }

}
