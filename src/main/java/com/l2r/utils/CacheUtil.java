package com.l2r.utils;

import com.l2r.dao.*;
import com.l2r.entity.*;
import com.l2r.entity.VO.CompanyVo;
import com.l2r.service.ICompany;
import com.l2r.service.IUser;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheOperationOutcomes;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 缓存基础数据 , 缓存的bean名称为"Dict"
 * Created by messi on 2019-12-23.
 */
@Service
public class CacheUtil {
    private static final Logger logger = LoggerFactory.getLogger(CacheUtil.class);
    @Autowired
    private IProvinceDao provinceDao;
    @Autowired
    private ICityDao cityDao;
    @Autowired
    private IIndustryDao industryDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private ICompanyDao companyDao;
    @Autowired
    private ISaleProjectDao saleProjectDao;
    @Autowired
    private IProductDao productDao;



    public void loadAllCache() {
        long t1 = new Date().getTime();
        initIndustry();
        long t2 = new Date().getTime();
        logger.info("=======initIndustry========== " + (t2-t1));
        initProvince();
        long t3 = new Date().getTime();
        logger.info("=======initProvince========== " +(t3-t2));
        initCity();
        long t4 = new Date().getTime();
        logger.info("=======initCity========== " + (t4-t3));
        initUser();
        long t5 = new Date().getTime();
        logger.info("=======initUser========== " + (t5-t4));
        initCompany();
        long t6 = new Date().getTime();
        logger.info("=======initCompany========== " + (t6-t5));
        initSaleProject();
        long t7 = new Date().getTime();
        logger.info("=======initSaleProject========== " + (t7-t6));
        initProduct();
        long t8 = new Date().getTime();
        logger.info("=======initProduct========== " + (t8-t7));
    }

    private void initProduct() {
        List<Product> products=new ArrayList<>();
        List<Object[]> productsVo=productDao.FindAllByVos();
        for(Object[] obj:productsVo){
            Product pd=new Product();
            pd.setId(obj[0]==null?null:new Integer((Integer) obj[0]));
            pd.setName(obj[1]==null?null:obj[1].toString());
            pd.setPrice(obj[2]==null?null:(BigDecimal)(obj[2]));
            pd.setCategory(obj[3]==null?null:obj[3].toString());
            products.add(pd);
        }
        Cache cache = SpringContextUtil.getBean("Dict", Cache.class);
        Element element = new Element("ProductCacheKey", products);
        cache.put(element);
    }
    public List<Product> getProducts(){
        Cache cache = SpringContextUtil.getBean("Dict", Cache.class);
        Element element = cache.get("ProductCacheKey");
        if (element == null || cache.isExpired(element)) {
           initProduct();
        }
        return (List<Product>) element.getObjectValue();
    }

    private void initSaleProject() {
        List<SaleProject> saleProjects=saleProjectDao.QueryALL();
        Cache cache = SpringContextUtil.getBean("Dict", Cache.class);
        Element element = new Element("SaleProjectCacheKey", saleProjects);
        cache.put(element);
    }

    public List<SaleProject> getSaleProjects(){
        Cache cache = SpringContextUtil.getBean("Dict", Cache.class);
        Element element = cache.get("SaleProjectCacheKey");
        if (element == null || cache.isExpired(element)) {
            List<SaleProject> saleProjects=saleProjectDao.QueryALL();
            element = new Element("SaleProjectCacheKey", saleProjects);
            cache.put(element);
        }
        return (List<SaleProject>) element.getObjectValue();
    }

    private void initCompany() {
        List<CompanyVo> companies=new ArrayList<>();
        List<Object[]> companyObjs=companyDao.FindAllByVos();
        companies=processingObj(companyObjs,companies);
        Cache cache = SpringContextUtil.getBean("Dict", Cache.class);
        Element element = new Element("CompanyCacheKey", companies);
        cache.put(element);
    }

    private List<CompanyVo> processingObj(List<Object[]> companyObjs, List<CompanyVo> companies) {
        for(Object[] obj:companyObjs){
            CompanyVo c=new CompanyVo();
            c.setCompanyId(obj[0]==null?null:obj[0].toString());
            c.setName(obj[1]==null?null:obj[1].toString());
            companies.add(c);
        }
        return companies;
    }

    public List<CompanyVo> getCompanys(){
        Cache cache = SpringContextUtil.getBean("Dict", Cache.class);
        Element element = cache.get("CompanyCacheKey");
        if (element == null || cache.isExpired(element)) {
            initCompany();
        }
        return (List<CompanyVo>) element.getObjectValue();
    }

    public void refreshCompany(Company newcompany, String s) {
        CompanyVo currentCompanyVo=new CompanyVo();
        currentCompanyVo.setCompanyId(newcompany.getCompanyId());
        currentCompanyVo.setName(newcompany.getName());
        Cache cache = SpringContextUtil.getBean("Dict", Cache.class);
        Element element = cache.get("CompanyCacheKey");
        if (element == null || cache.isExpired(element)) {
            initCompany();
            element = cache.get("CompanyCacheKey");
        }
        List<CompanyVo> companyVos=(List<CompanyVo>) element.getObjectValue();
        if(s.equals("Create")){
            companyVos.add(currentCompanyVo);
        }else if (s.equals("Update")){
            CompanyVo orgC=null;
            for(CompanyVo c:companyVos){
                if(c.getCompanyId().equals(currentCompanyVo.getCompanyId())){
                    orgC=c;
                }
            }
            companyVos.remove(orgC);
            companyVos.add(currentCompanyVo);
        }else if(s.equals("Delete")){
            companyVos.remove(currentCompanyVo);
        }
        Element Newelement = new Element("CompanyCacheKey", companyVos);
        cache.put(Newelement);
    }

    public void insertCompanyCache(Company company) {
        CompanyVo currentCompanyVo=new CompanyVo();
        currentCompanyVo.setCompanyId(company.getCompanyId());
        currentCompanyVo.setName(company.getName());
        Cache cache = SpringContextUtil.getBean("Dict", Cache.class);
        Element element = cache.get("CompanyCacheKey");
        if (element == null || cache.isExpired(element)) {
            initCompany();
            element = cache.get("CompanyCacheKey");
        }
        List<CompanyVo> companyVos=(List<CompanyVo>) element.getObjectValue();
        companyVos.add(currentCompanyVo);
        Element Newelement = new Element("CompanyCacheKey", companyVos);
        cache.put(Newelement);
    }



    public void initIndustry() {
//        List<Industry> industryList = industryDao.findAll();
        List<Industry> industryList = industryDao.QueryALL();
        Cache cache = SpringContextUtil.getBean("Dict", Cache.class);
        Element element = new Element("IndustryCacheKey", industryList);
        cache.put(element);
    }
    public List<Industry> getIndustries() {
        Cache cache = SpringContextUtil.getBean("Dict", Cache.class);
        Element element = cache.get("IndustryCacheKey");
        if (element == null || cache.isExpired(element)) {
            List<Industry> industryList = industryDao.QueryALL();
            element = new Element("IndustryCacheKey", industryList);
            cache.put(element);
        }
        return (List<Industry>) element.getObjectValue();
    }

    public void initProvince(){
        List<Province> provinces=provinceDao.QueryALL();
        Cache cache = SpringContextUtil.getBean("Dict", Cache.class);
        Element element = new Element("ProvinceCacheKey", provinces);
        cache.put(element);
    }
    public List<Province> getProvinces(){
        Cache cache=SpringContextUtil.getBean("Dict",Cache.class);
        Element element=cache.get("ProvinceCacheKey");
        if (element == null || cache.isExpired(element)) {
            List<Province> provinces=provinceDao.QueryALL();
            element = new Element("ProvinceCacheKey", provinces);
            cache.put(element);
        }
        return (List<Province>) element.getObjectValue();
    }

    public void initCity(){
        List<City> cities=cityDao.QueryALL();
        Cache cache = SpringContextUtil.getBean("Dict", Cache.class);
        Element element = new Element("CityCacheKey", cities);
        cache.put(element);
    }
    public List<City> getCities(){
        Cache cache=SpringContextUtil.getBean("Dict",Cache.class);
        Element element=cache.get("CityCacheKey");
        if (element == null || cache.isExpired(element)) {
            List<City> cities=cityDao.QueryALL();
            element = new Element("CityCacheKey", cities);
            cache.put(element);
        }
        return (List<City>) element.getObjectValue();
    }

    public void initUser(){
        List<User> users=userDao.QueryALL();
        Cache cache=SpringContextUtil.getBean("Dict",Cache.class);
        Element element=new Element("UserCacheKey",users);
        cache.put(element);
    }

    public List<User> getUsers(){
        Cache cache=SpringContextUtil.getBean("Dict",Cache.class);
        Element element=cache.get("UserCacheKey");
        if(element==null||cache.isExpired(element)){
            List<User> users=userDao.QueryALL();
            element=new Element("UserCacheKey",users);
            cache.put(element);
        }
        return (List<User>) element.getObjectValue();
    }



}
