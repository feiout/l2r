package com.l2r.service.Impl;

import com.l2r.base.AbstractService;
import com.l2r.dao.ICompanyDao;
import com.l2r.dao.ICompany_logsDao;
import com.l2r.dao.ICompany_relationDao;
import com.l2r.entity.*;
import com.l2r.entity.VO.CompanyVo;
import com.l2r.exceptions.AccountExistException;
import com.l2r.service.ICompany;
import com.l2r.service.ISerialNum;
import com.l2r.utils.CacheUtil;
import com.l2r.utils.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by messi on 2019-12-20.
 */
@Service
public class ICompanyImpl extends AbstractService<Company> implements ICompany {

    @Autowired
    private ICompanyDao companyDao;
    @Autowired
    private ICompany_logsDao company_logsDao;
    @Autowired
    private ICompany_relationDao company_relationDao;
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private ISerialNum serialNumService;

    @Override
    public JpaRepository<Company, String> getRepository() {
        return companyDao;
    }


    @Override
    public Company InsertOrUpdate(Company company,User currentUser) throws AccountExistException {
        //True for inserting; false for updating
        boolean isAdding = true;
        Company oldCompany = new Company();
        List<Company> dup=new ArrayList<>();
        String Logs=null;
        if (null == company.getCompanyId()) {
            oldCompany.setCreateDate(new Date());
            oldCompany.setCompanyId(serialNumService.getSerialNum(SerialNum.SerialTypeEnum.COMPANY_ID, "COM"));
            dup  = companyDao.findByName(company.getName());
        }else
        {
            isAdding = false; //更新company
            oldCompany =companyDao.FindById(company.getId());
            //比对改动并记录company的events
            Logs=getComparasion(oldCompany,company);
            dup = companyDao.findByNameAndId(company.getName(), company.getId());
        }
        if (!dup.isEmpty()) {
            throw new AccountExistException();
        }
        //设置company
        oldCompany.setName(company.getName());
        oldCompany.setIndustry(company.getIndustry());
        oldCompany.setAddress(company.getAddress());
        oldCompany.setProvince(company.getProvince());
        oldCompany.setCity(company.getCity());
        oldCompany.setPriority(company.getPriority());
        Company newcompany=companyDao.save(oldCompany);
//        cacheUtil.refreshCompany(newcompany, isAdding ? "Create" : "Update");  //更新缓存

        //设置company_logs
        Company_logs company_logs=new Company_logs();
        company_logs.setCompanyId(newcompany.getCompanyId());
        company_logs.setNote(company.getNote());
        company_logs.setEvents(Logs);
        company_logs.setModifyBy(currentUser.getUserId());
        company_logs.setUpdateDate(new Date());
        company_logs=company_logsDao.save(company_logs);

        //设置company的parent关系
        setParentRelation(newcompany.getCompanyId(),company.getParentId());
        return newcompany;
    }

    @Override
    public Company InsertCompany(Company company, User currentUser) throws AccountExistException {
        boolean isAdding = true;
        Company oldCompany = new Company();
        List<Company> dup;
        String Logs=null;
        String parentId="-1";
        oldCompany.setCreateDate(new Date());
        oldCompany.setCompanyId(serialNumService.getSerialNum(SerialNum.SerialTypeEnum.COMPANY_ID, "COM"));
        dup  = companyDao.findByName(company.getName());
        if (!dup.isEmpty()) {
            throw new AccountExistException();
        }
        //设置company
        oldCompany.setName(company.getName());
        oldCompany.setIndustry(company.getIndustry());
        oldCompany.setAddress(company.getAddress());
        oldCompany.setProvince(company.getProvince());
        oldCompany.setCity(company.getCity());
        oldCompany.setPriority(company.getPriority());
        Company newcompany=companyDao.save(oldCompany);
//        cacheUtil.refreshCompany(newcompany, isAdding ? "Create" : "Update");  //更新缓存
        //设置company_logs
        Company_logs company_logs=new Company_logs();
        company_logs.setCompanyId(newcompany.getCompanyId());
        company_logs.setNote(company.getNote());
        company_logs.setEvents(Logs);
        company_logs.setModifyBy(currentUser.getUserId());
        company_logs.setUpdateDate(new Date());
        company_logsDao.save(company_logs);
        //设置company的parent关系
        if (!ObjectUtil.isNullOrEmpty(company.getParentId())) {
            parentId=company.getParentId();
        }
        setParentRelation(newcompany.getCompanyId(),parentId);
        return newcompany;
    }

    @Override
    public List<CompanyVo> FindByComapnyNameLike(String name) {
        List<CompanyVo> companies=new ArrayList<>();
        List<Object[]> companyObjs=companyDao.FindAllVosByNameLike(name);
        for(Object[] obj:companyObjs){
            CompanyVo c=new CompanyVo();
            c.setCompanyId(obj[0]==null?null:obj[0].toString());
            c.setName(obj[1]==null?null:obj[1].toString());
            companies.add(c);
        }
        return companies;
    }


    @Override
    public void setParentRelation(String companyId, String parentId) {
        Company_relation oldCr=company_relationDao.FindbySubCompanyId(companyId);
        if(!parentId.equals("-1")) {
            Company_relation cr=company_relationDao.FindbyParentAndSubId(parentId,companyId);
            if (null==cr) {
                if(null!=oldCr)company_relationDao.delete(oldCr);
                Company_relation scr=new Company_relation();
                scr.setParentCompanyId(parentId);
                scr.setSubCompanyId(companyId);
                scr.setCreateDate(new Date());
                company_relationDao.save(scr);
            }
        }
        else {
            if(null!=oldCr)company_relationDao.delete(oldCr);
        }
    }

    @Override
    public Company FindByComapnyId(String companyId) {
        Company company=companyDao.FindByCompanyId(companyId);
        return company;
    }

    @Override
    public Company_logs FindLogsByCompanyId(String companyId) {
        Company_logs company_logs=company_logsDao.FindByCompanyId(companyId);
        return company_logs;
    }

    @Override
    public Company FindCrByCompanyId(String companyId) {
        Company cr=companyDao.FindParentByCompanyId(companyId);
        return cr;
    }


    private String getComparasion(Company oldCompany, Company company) {
        String Logs=null;
        if(oldCompany.getName()!=company.getName()){
            Logs = "Name: " + oldCompany.getName() + "-->" + company.getName();
        }
        if(oldCompany.getIndustry()!=company.getIndustry()){
            if (Logs == null)
            {
                Logs = "Industry: " + oldCompany.getIndustry() + "-->" + company.getIndustry();
            }
            else
            {
                Logs = Logs + "\r\n" + "Industry: " + oldCompany.getIndustry() + "-->" + company.getIndustry();
            }
        }
        if(oldCompany.getAddress()!=company.getAddress()){
            if (Logs == null)
            {
                Logs = "Address: " + oldCompany.getAddress() + "-->" + company.getAddress();
            }
            else
            {
                Logs = Logs + "\r\n" + "Address: " + oldCompany.getAddress() + "-->" + company.getAddress();
            }
        }
        if(oldCompany.getProvince()!=company.getProvince()){
            if (Logs == null)
            {
                Logs = "Province: " + oldCompany.getProvince() + "-->" + company.getProvince();
            }
            else
            {
                Logs = Logs + "\r\n" + "Province: " + oldCompany.getProvince() + "-->" + company.getProvince();
            }
        }
        if (oldCompany.getCity()!=company.getCity()){
            if (Logs == null)
            {
                Logs = "City: " + oldCompany.getCity() + "-->" + company.getCity();
            }
            else
            {
                Logs = Logs + "\r\n" + "City: " + oldCompany.getCity() + "-->" + company.getCity();
            }
        }
        if(oldCompany.getPriority()!=company.getPriority()){
            if (Logs == null)
            {
                Logs = "Priority: " + oldCompany.getPriority() + "-->" + company.getPriority();
            }
            else
            {
                Logs = Logs + "\r\n" + "Priority: " + oldCompany.getPriority() + "-->" + company.getPriority();
            }
        }
        return Logs;
    }
    
}
