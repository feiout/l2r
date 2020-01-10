package com.l2r.service.Impl;

import com.l2r.base.AbstractService;
import com.l2r.dao.ICustomerDao;
import com.l2r.dao.ICustomer_logsDao;
import com.l2r.dao.ICustomer_relationDao;
import com.l2r.entity.*;
import com.l2r.exceptions.ContactExistException;
import com.l2r.service.ICustomer;
import com.l2r.service.ISerialNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Messi on 2019/11/18.
 */

@Service
public class ICustomerImpl extends AbstractService<Customer> implements ICustomer {
    @Autowired
    private ICustomerDao customerDao;
    @Autowired
    private ICustomer_logsDao customer_logsDao;
    @Autowired
    private ICustomer_relationDao customer_relationDao;
    @Autowired
    private ISerialNum serialNumService;

    @Override
    public JpaRepository<Customer, String> getRepository() {
        return customerDao;
    }


    @Override
    public Customer InsertOrUpdate(Company company, Customer customer, User currentUser) throws ContactExistException {
        Customer oldCustomer=new Customer();
        List<Customer> dup=new ArrayList<>();
        String Logs=null;
        if(null==customer.getCustomerId()){
            dup = customerDao.FindByNameAndEmailAndCellphoneAndWorkphone(customer.getName(),customer.getEmail(),customer.getCellphone(),customer.getWorkphone());
            oldCustomer.setCreateDate(new Date());
            oldCustomer.setCustomerId(serialNumService.getSerialNum(SerialNum.SerialTypeEnum.CUSTOMER_ID, "CUS"));
        }
        else
        {
            oldCustomer=customerDao.FindById(customer.getId());
            //比对改动并记录company的events
            Logs=getComparasion(oldCustomer,customer);
            dup = customerDao.FindByNameAndEmailAndCellphoneAndWorkphoneAndId(customer.getName(),customer.getEmail(),customer.getCellphone(),customer.getWorkphone(),customer.getId());
        }
        if (!dup.isEmpty()) {
            throw new ContactExistException();
        }
        //设置customer
        oldCustomer.setName(customer.getName());
        oldCustomer.setCellphone(customer.getCellphone());
        oldCustomer.setWorkphone(customer.getWorkphone());
        oldCustomer.setGender(customer.getGender());
        oldCustomer.setEmail(customer.getEmail());
        oldCustomer.setTitle(customer.getTitle());
        oldCustomer.setDepartment(customer.getDepartment());
        oldCustomer.setNextVisit(customer.getNextVisit());
        Customer newCustomer=customerDao.save(oldCustomer);

        //设置customer_logs
        Customer_logs customerLogs=new Customer_logs();
        customerLogs.setCustomerId(newCustomer.getCustomerId());
        customerLogs.setNote(customer.getNote());
        customerLogs.setEvents(Logs);
        customerLogs.setModifyBy(currentUser.getUserId());
        customerLogs.setUpdateDate(new Date());
        customerLogs=customer_logsDao.save(customerLogs);

        //设置companyRelation (company + customer)
        setCustomerCompanyRelation(newCustomer.getCustomerId(),company.getCompanyId());
        return newCustomer;
    }

    @Override
    public List<Customer> FindByCompanyId(String companyId) {
        List<Customer> customers=customerDao.FindByCompanyId(companyId);
        return  customers;
    }

    private void setCustomerCompanyRelation(String customerId, String companyId) {
        Customer_relation oldCr=customer_relationDao.FindbyCustomerIdAndCompanyId(customerId,companyId);
        if(null==oldCr){
            Customer_relation cr=customer_relationDao.FindbyCustomerId(customerId);
            if(null!=cr)customer_relationDao.delete(cr);
            Customer_relation scr=new Customer_relation();
            scr.setCreateDate(new Date());
            scr.setCustomerId(customerId);
            scr.setCompanyId(companyId);
            customer_relationDao.save(scr);
        }
    }

    private String getComparasion(Customer oldCustomer, Customer customer) {
        String Logs=null;
        if(oldCustomer.getName()!=customer.getName()){
            Logs = "Name: " + oldCustomer.getName() + "-->" + customer.getName();
        }
        if(oldCustomer.getGender()!=customer.getGender()){
            if (Logs == null)
            {
                Logs = "Gender: " + oldCustomer.getGender() + "-->" + customer.getGender();
            }
            else
            {
                Logs = Logs + "\r\n" + "Gender: " + oldCustomer.getGender() + "-->" + customer.getGender();
            }
        }
        if(oldCustomer.getDepartment()!=customer.getDepartment()){
            if (Logs == null)
            {
                Logs = "Department: " + oldCustomer.getDepartment() + "-->" + customer.getDepartment();
            }
            else
            {
                Logs = Logs + "\r\n" + "Department: " + oldCustomer.getDepartment() + "-->" + customer.getDepartment();
            }
        }
        if(oldCustomer.getTitle()!=customer.getTitle()){
            if (Logs == null)
            {
                Logs = "Title: " + oldCustomer.getTitle() + "-->" + customer.getTitle();
            }
            else
            {
                Logs = Logs + "\r\n" + "Title: " + oldCustomer.getTitle() + "-->" + customer.getTitle();
            }
        }
        if(oldCustomer.getEmail()!=customer.getEmail()){
            if (Logs == null)
            {
                Logs = "Email: " + oldCustomer.getEmail() + "-->" + customer.getEmail();
            }
            else
            {
                Logs = Logs + "\r\n" + "Email: " + oldCustomer.getEmail() + "-->" + customer.getEmail();
            }
        }
        if(oldCustomer.getCellphone()!=customer.getCellphone()){
            if (Logs == null)
            {
                Logs = "Cellphone: " + oldCustomer.getCellphone() + "-->" + customer.getCellphone();
            }
            else
            {
                Logs = Logs + "\r\n" + "Cellphone: " + oldCustomer.getCellphone() + "-->" + customer.getCellphone();
            }
        }
        if(oldCustomer.getWorkphone()!=customer.getWorkphone()){
            if (Logs == null)
            {
                Logs = "Workphone: " + oldCustomer.getWorkphone() + "-->" + customer.getWorkphone();
            }
            else
            {
                Logs = Logs + "\r\n" + "Workphone: " + oldCustomer.getWorkphone() + "-->" + customer.getWorkphone();
            }
        }

        return Logs;
    }
}
