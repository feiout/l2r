package com.l2r.service;

import com.l2r.base.IService;
import com.l2r.entity.*;
import com.l2r.entity.VO.CompanyVo;
import com.l2r.exceptions.AccountExistException;

import java.util.List;

/**
 * Created by messi on 2019-12-20.
 */
public interface ICompany extends IService<Company> {

    Company InsertOrUpdate(Company company,User currentUser) throws AccountExistException;

    void setParentRelation(String companyId, String parentId);

    Company FindByComapnyId(String companyId);

    Company_logs FindLogsByCompanyId(String companyId);

    Company FindCrByCompanyId(String companyId);

    Company InsertCompany(Company company, User currentUser) throws AccountExistException;

    List<CompanyVo> FindByComapnyNameLike(String name);
}
