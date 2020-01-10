package com.l2r.service.Impl;

import com.l2r.base.AbstractService;
import com.l2r.base.Result;
import com.l2r.dao.IHwcaseDao;
import com.l2r.dao.IHwcase_logsDao;
import com.l2r.dao.IHwcase_relationDao;
import com.l2r.entity.*;
import com.l2r.entity.VO.ReturnVo;
import com.l2r.exceptions.AccountExistException;
import com.l2r.exceptions.ContactExistException;
import com.l2r.service.ICompany;
import com.l2r.service.ICustomer;
import com.l2r.service.IHwcase;
import com.l2r.service.ISerialNum;
import com.l2r.utils.CacheUtil;
import com.l2r.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by messi on 2019-12-20.
 */
@Service
public class IHwcaseImpl extends AbstractService<Hwcase> implements IHwcase {

    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private IHwcaseDao caseDao;
    @Autowired
    private ICompany companyService;
    @Autowired
    private ICustomer customerService;
    @Autowired
    private ISerialNum serialNumService;
    @Autowired
    private IHwcase_logsDao hwcase_logsDao;
    @Autowired
    private IHwcase_relationDao hwcase_relationDao;
    @Autowired
    private CacheUtil cacheUtil;

    @PersistenceContext
    private EntityManager em;

    //查询的表
    private final static String FROM_HQL = "from hwcase c inner JOIN hwcase_relation hr on hr.cId=c.caseId";

    //排序条件
    private final static String SORT_HQL = " ORDER BY c.caseId DESC";

    //返回值
    private final static String RESULT_HQL = "select c.caseId,c.type,c.source,c.companyId,c.companyName,c.customerId,c.customerName,c.cellphone,c.workphone,c.pendingDate ";

    //计算总数
    private final static String COUNT_HQL = " select COUNT(c.id)";

    @Override
    public Page<ReturnVo> filterPagedCases(Date createdStartDate, Date createdEndDate, Date pendingStartDate, Date pendingEndDate, String saleProjectId, String status, User currentUser, Pageable pageRequest) {
        //获取查询条件和参数
        Map<String, Object> map = new HashMap<>();
        String optionHql = getOptionHql(currentUser, createdStartDate,createdEndDate,pendingStartDate,pendingEndDate,saleProjectId,status,map);
        String countHql = COUNT_HQL + FROM_HQL + optionHql;
        String selectHql = RESULT_HQL + FROM_HQL + optionHql + SORT_HQL;

        Query query=em.createNativeQuery(selectHql);
        Query countQuery = em.createNativeQuery(countHql);


        //设置查询参数
        for (String key : map.keySet()) {
            query.setParameter(key, map.get(key));
            countQuery.setParameter(key, map.get(key));
        }

        //分页起始值
        Integer startIndex = pageRequest.getPageNumber() * pageRequest.getPageSize();
        query.setFirstResult(startIndex);
        query.setMaxResults(pageRequest.getPageSize());

        //总数
        long totalSize = Long.parseLong(countQuery.getSingleResult().toString());
//        List<Map<String,Object>> list = query.getResultList();
        List<Object[]> list = query.getResultList();
        List<ReturnVo> rVos = getReturnVo(list);
        Page<ReturnVo> page = new PageImpl<>(rVos, pageRequest, totalSize);
        return page;
    }

    private List<ReturnVo> getReturnVo(List<Object[]> list) {
        List<ReturnVo> rVos=new ArrayList<>();
        for(Object[] obj:list){
            ReturnVo r=new ReturnVo();
            r.setCaseId(obj[0]==null?null:obj[0].toString());
            r.setType(obj[1]==null?null:obj[1].toString());
            r.setSource(obj[2]==null?null:obj[2].toString());
            r.setCompanyId(obj[3]==null?null:obj[3].toString());
            r.setCompanyName(obj[4]==null?null:obj[4].toString());
            r.setCustomerId(obj[5]==null?null:obj[5].toString());
            r.setCustomerName(obj[6]==null?null:obj[6].toString());
            r.setCellphone(obj[7]==null?null:obj[7].toString());
            r.setWorkphone(obj[8]==null?null:obj[8].toString());
            r.setPendingDate(obj[9]==null?null:obj[9].toString().substring(0,obj[9].toString().indexOf(" ")));
            rVos.add(r);
        }
        return rVos;
    }


    private String getOptionHql(User currentUser, Date createdStartDate, Date createdEndDate, Date pendingStartDate, Date pendingEndDate, String saleProjectId, String status, Map<String, Object> map) {
        StringBuilder sb = new StringBuilder(" where 1=1 ");
        if (null != saleProjectId && !saleProjectId.equals("-1")) {
            sb.append(" AND c.saleProjectId =:saleProjectId");
            map.put("saleProjectId", saleProjectId);
        }
        if (null != status && !status.equals("-1")) {
            sb.append(" AND c.status =:status");
            map.put("status", status);
        }
        if (null != createdStartDate) {
            sb.append(" AND c.createDate >=:createdStartDate");
            map.put("createdStartDate", createdStartDate);
        }
        if (null != createdEndDate) {
            Date createEndDateEndOfDay = DateUtil.convertToEndOfDay(createdEndDate);
            sb.append(" AND c.createDate <=:createdEndDate");
            map.put("createdEndDate", createEndDateEndOfDay);
        }
        if (null != pendingStartDate) {
            sb.append(" AND c.pendingDate >=:pendingStartDate");
            map.put("pendingStartDate", pendingStartDate);
        }
        if (null != pendingEndDate) {
            Date pendingEndDateEndOfDay = DateUtil.convertToEndOfDay(pendingEndDate);
            sb.append(" AND c.pendingDate <=:pendingEndDate");
            map.put("pendingEndDate", pendingEndDateEndOfDay);
        }

        return sb.toString();
    }


    @Override
    public JpaRepository<Hwcase, String> getRepository() {
        return caseDao;
    }

    @Override
    public List<Hwcase> findListByCustomerId(Integer customerId) {
        List<Hwcase> hwcases=caseDao.findListByCustomerId(customerId);
        return hwcases;
    }

    @Override
    public List<Hwcase> findListByCompanyId(Integer companyId) {
        List<Hwcase> hwcases=caseDao.findListByCompanyId(companyId);
        return hwcases;
    }

    @Override
    public Hwcase findById(Integer cId) {
        Hwcase hwcase=caseDao.findByCaseId(cId);
        return hwcase;
    }

    @Override
    @Transactional
    public Hwcase InsertCase(Hwcase theCase, User currentUser) {
        Hwcase oldCase=new Hwcase();
        String Logs=null;
        Company company = new Company();
        Customer customer = new Customer();
        String ownType="";

        if(null==theCase.getCompanyId()) {
            //创建company
            boolean isAdding = true;
            if (null != theCase.getCompanyName()) company.setName(theCase.getCompanyName());
            if (null != theCase.getAddress()) company.setAddress(theCase.getAddress());
            if (null != theCase.getCity()) company.setCity(theCase.getCity());
            if (null != theCase.getProvince()) company.setProvince(theCase.getProvince());
            if (null != theCase.getIndustry()) company.setIndustry(theCase.getIndustry());
            if (null != theCase.getPriority()) company.setPriority(theCase.getPriority());
            if (null != theCase.getCompanyNote()) company.setNote(theCase.getCompanyNote());
            if (null != theCase.getParentId()) company.setParentId(theCase.getParentId());
            try {
//                company = companyService.InsertOrUpdate(company, currentUser);
                company = companyService.InsertCompany(company, currentUser);
                oldCase.setCompanyId(company.getCompanyId());
                cacheUtil.insertCompanyCache(company);  //更新缓存
            } catch (AccountExistException ae) {
                throw ae;
            }
        }
        else
        {
            oldCase.setCompanyId(theCase.getCompanyId());
            company.setCompanyId(theCase.getCompanyId());
        }
        if(null==theCase.getCustomerId()) {
            //创建Customer
            if (null != theCase.getCustomerName()) customer.setName(theCase.getCustomerName());
            if (null != theCase.getCellphone()) customer.setCellphone(theCase.getCellphone());
            if (null != theCase.getWorkphone()) customer.setWorkphone(theCase.getWorkphone());
            if (null != theCase.getGender()) customer.setGender(Integer.parseInt(theCase.getGender()));
            if (null != theCase.getEmail()) customer.setEmail(theCase.getEmail());
            if (null != theCase.getTitle()) customer.setTitle(theCase.getTitle());
            if (null != theCase.getDepartment()) customer.setDepartment(theCase.getDepartment());
            if (null != theCase.getCustomerNote()) customer.setNote(theCase.getCustomerNote());
            //*
            //创建case时自动对联系人120天之后产生一个回访TASK
            //*
            Date nextVisit = new Date();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(nextVisit);
            calendar.add(calendar.DATE, 120);
            nextVisit = calendar.getTime();
            customer.setNextVisit(nextVisit);
            try {
                customer = customerService.InsertOrUpdate(company, customer, currentUser);
                oldCase.setCustomerId(customer.getCustomerId());
                //更新缓存
            } catch (ContactExistException ce) {
                throw ce;
            }
        }
        else
        {
            oldCase.setCustomerId(theCase.getCustomerId());
        }

        oldCase.setCaseId(serialNumService.getSerialNum(SerialNum.SerialTypeEnum.CASE_ID, "C"));
        oldCase.setType(theCase.getType());
        oldCase.setSource(theCase.getSource());
        oldCase.setPendingDate(theCase.getPendingDate());
        oldCase.setCompanyName(theCase.getCompanyName());
        oldCase.setCustomerName(theCase.getCustomerName());
        oldCase.setCellphone(theCase.getCellphone());
        oldCase.setWorkphone(theCase.getWorkphone());
        oldCase.setStatus(theCase.getStatus());
        oldCase.setSaleProjectId(theCase.getSaleProjectId());
        oldCase.setSaleProjectName(theCase.getSaleProjectName());
        oldCase.setCreateDate(new Date());
        oldCase.setCallType(theCase.getCallType());
        oldCase.setSubscriptionId(currentUser.getSubscriptionId());
        oldCase.setUserId(theCase.getUserId());
        Hwcase saveCase=caseDao.save(oldCase);

        //设置caseLogs
        Hwcase_logs hwcase_logs=new Hwcase_logs();
        hwcase_logs.setcId(saveCase.getCaseId());
        hwcase_logs.setNote(theCase.getCaseNote());
        hwcase_logs.setEvents(Logs);
        hwcase_logs.setModifyBy(currentUser.getUserId());
        hwcase_logs.setUpdateDate(new Date());
        hwcase_logsDao.save(hwcase_logs);

        //设置case和user的对应关系
        if (theCase.getUserId().equals(currentUser.getUserId()) ) {
            ownType = "owner";
            createHwcaseRelation(ownType,oldCase.getCaseId(),currentUser.getUserId());
        } else {
            ownType = "modify";
            createHwcaseRelation(ownType,oldCase.getCaseId(),currentUser.getUserId());
            ownType = "owner";
            createHwcaseRelation(ownType,oldCase.getCaseId(),theCase.getUserId());
        }
        return saveCase;
    }




//    @Override
//    @Transactional
//    public Hwcase InsertOrUpdate(Hwcase theCase, User currentUser) {
//        Hwcase oldCase=new Hwcase();
//        String Logs=null;
//        Company company = new Company();
//        Customer customer = new Customer();
//        Hwcase_relation oldhwcr;
//        String ownType="";
//
//        if(null==theCase.getCompanyId()) {
//            //创建company
//            boolean isAdding = true;
//            if (null != theCase.getCompanyName()) company.setName(theCase.getCompanyName());
//            if (null != theCase.getAddress()) company.setAddress(theCase.getAddress());
//            if (null != theCase.getCity()) company.setCity(theCase.getCity());
//            if (null != theCase.getProvince()) company.setProvince(theCase.getProvince());
//            if (null != theCase.getIndustry()) company.setIndustry(theCase.getIndustry());
//            if (null != theCase.getPriority()) company.setPriority(theCase.getPriority());
//            if (null != theCase.getCompanyNote()) company.setNote(theCase.getCompanyNote());
//            if (null != theCase.getParentId()) company.setParentId(theCase.getParentId());
//            try {
//                company = companyService.InsertOrUpdate(company, currentUser);
//                oldCase.setCompanyId(company.getCompanyId());
//                //更新缓存
//                cacheUtil.refreshCompany(company, isAdding ? "Create" : "Update");  //更新缓存
//            } catch (AccountExistException ae) {
//                throw ae;
//            }
//        }
//        if(null==theCase.getCustomerId()) {
//            //创建Customer
//            if (null != theCase.getCustomerName()) customer.setName(theCase.getCustomerName());
//            if (null != theCase.getCellphone()) customer.setCellphone(theCase.getCellphone());
//            if (null != theCase.getWorkphone()) customer.setWorkphone(theCase.getWorkphone());
//            if (null != theCase.getGender()) customer.setGender(Integer.parseInt(theCase.getGender()));
//            if (null != theCase.getEmail()) customer.setEmail(theCase.getEmail());
//            if (null != theCase.getTitle()) customer.setTitle(theCase.getTitle());
//            if (null != theCase.getDepartment()) customer.setDepartment(theCase.getDepartment());
//            if (null != theCase.getCustomerNote()) customer.setNote(theCase.getCustomerNote());
//            //*
//            //创建case时自动对联系人120天之后产生一个回访TASK
//            //*
//            Date nextVisit = new Date();
//            Calendar calendar = new GregorianCalendar();
//            calendar.setTime(nextVisit);
//            calendar.add(calendar.DATE, 120);
//            nextVisit = calendar.getTime();
//            customer.setNextVisit(nextVisit);
//            try {
//                customer = customerService.InsertOrUpdate(company, customer, currentUser);
//                oldCase.setCustomerId(customer.getCustomerId());
//                //更新缓存
//            } catch (ContactExistException ce) {
//                throw ce;
//            }
//        }
//        oldCase.setCaseId(serialNumService.getSerialNum(SerialNum.SerialTypeEnum.CASE_ID, "C"));
//        oldCase.setType(theCase.getType());
//        oldCase.setSource(theCase.getSource());
//        oldCase.setPendingDate(theCase.getPendingDate());
//        oldCase.setCompanyName(theCase.getCompanyName());
//        oldCase.setCustomerName(theCase.getCustomerName());
//        oldCase.setCellphone(theCase.getCellphone());
//        oldCase.setWorkphone(theCase.getWorkphone());
//        oldCase.setStatus(theCase.getStatus());
//        oldCase.setSaleProjectId(theCase.getSaleProjectId());
//        oldCase.setSaleProjectName(theCase.getSaleProjectName());
//        oldCase.setCreateDate(new Date());
//        oldCase.setCallType(theCase.getCallType());
//        oldCase.setSubscriptionId(currentUser.getSubscriptionId());
//        oldCase.setUserId(theCase.getUserId());
//        Hwcase saveCase=caseDao.save(oldCase);
//
//
//        //设置caseLogs
//        Hwcase_logs hwcase_logs=new Hwcase_logs();
//        hwcase_logs.setcId(saveCase.getCaseId());
//        hwcase_logs.setNote(theCase.getCaseNote());
//        hwcase_logs.setEvents(Logs);
//        hwcase_logs.setModifyBy(currentUser.getUserId());
//        hwcase_logs.setUpdateDate(new Date());
//        hwcase_logsDao.save(hwcase_logs);
//
//        //设置case和user的对应关系
//        if (theCase.getUserId().equals(currentUser.getUserId()) ) {
//            ownType = "owner";
//            createHwcaseRelation(ownType,oldCase.getCaseId(),currentUser.getUserId());
//        } else {
//            ownType = "modify";
//            createHwcaseRelation(ownType,oldCase.getCaseId(),currentUser.getUserId());
//            ownType = "owner";
//            createHwcaseRelation(ownType,oldCase.getCaseId(),theCase.getUserId());
//        }
//        return saveCase;
//    }

    private void setHwcaseRelation(String ownType, String caseId, String userId, Hwcase_relation oldhwcr) {
        if (null == oldhwcr.getId()) {
            createHwcaseRelation(ownType,caseId,userId);
        }
    }

    private void createHwcaseRelation(String ownType, String caseId, String userId) {
        Hwcase_relation hwcr=new Hwcase_relation();
        hwcr.setcId(caseId);
        hwcr.setUserId(userId);
        hwcr.setCreateDate(new Date());
        hwcr.setOwnType(ownType);
        hwcase_relationDao.save(hwcr);
    }


    private String getComparasion(Hwcase newCase, Hwcase oldCase) {
        String Logs=null;
        Hwcase_logs hwcase_logs=hwcase_logsDao.FindyByCid(oldCase.getId());
        if(oldCase.getType()!=newCase.getType()){
            Logs = "Type: " + oldCase.getType() + "-->" + newCase.getType();
        }
        if(oldCase.getSource()!=newCase.getSource()){
            if (Logs == null)
            {
                Logs = "Source: " + oldCase.getSource() + "-->" + newCase.getSource();
            }
            else
            {
                Logs = Logs + "\r\n" + "Source: " + oldCase.getSource() + "-->" + newCase.getSource();
            }
        }
        if (oldCase.getPendingDate()!=newCase.getPendingDate())
        {
            if (Logs == null)
            {
                Logs = "PendingDate: " + oldCase.getPendingDate() + "-->" + newCase.getPendingDate();
            }
            else
            {
                Logs = Logs + "\r\n" + "PendingDate: " + oldCase.getPendingDate() + "-->" + newCase.getPendingDate();
            }
        }
        if(oldCase.getStatus()!=newCase.getStatus()){
            if (Logs == null)
            {
                Logs = "Status: " + oldCase.getStatus() + "-->" + newCase.getStatus();
            }
            else
            {
                Logs = Logs + "\r\n" + "Status: " + oldCase.getStatus() + "-->" + newCase.getStatus();
            }
        }
        if(hwcase_logs.getNote()!=newCase.getCaseNote()){
            if (Logs == null)
            {
                Logs = "CaseNote: " + hwcase_logs.getNote() + "-->" + newCase.getCaseNote();
            }
            else
            {
                Logs = Logs + "\r\n" + "CaseNote: " + hwcase_logs.getNote() + "-->" + newCase.getCaseNote();
            }
        }

        return Logs;
    }
}
