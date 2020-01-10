package com.l2r.service;

import com.l2r.base.IService;
import com.l2r.entity.Company;
import com.l2r.entity.Customer;
import com.l2r.entity.User;
import com.l2r.exceptions.ContactExistException;


import java.util.List;

/**
 * Created by Messi on 2019/11/18.
 */


public interface ICustomer extends IService<Customer> {

    Customer InsertOrUpdate(Company company, Customer customer, User currentUser) throws ContactExistException;

    List<Customer> FindByCompanyId(String companyId);
}
