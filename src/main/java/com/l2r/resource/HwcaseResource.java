package com.l2r.resource;

import com.l2r.base.BaseResource;
import com.l2r.base.Result;
import com.l2r.entity.Hwcase;
import com.l2r.entity.Page.PagedCase;
import com.l2r.entity.User;
import com.l2r.entity.VO.ResultVo;
import com.l2r.entity.VO.ReturnVo;
import com.l2r.entity.VO.SearchDto;
import com.l2r.exceptions.ContactExistException;
import com.l2r.service.ICompany;
import com.l2r.service.ICustomer;
import com.l2r.service.IHwcase;
import com.l2r.utils.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import com.l2r.exceptions.AccountExistException;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by messi on 2019-12-20.
 */

@RestController
@RequestMapping("/Hwcase")
public class HwcaseResource extends BaseResource {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IHwcase iHwcase;
    @Autowired
    private ICompany iCompany;
    @Autowired
    private ICustomer iCustomer;

    //获取customer对应的case列表
    @RequestMapping(value="/listByCustomerId/{customerId}",method = RequestMethod.GET)
    public Result getListByCustomerId(HttpServletRequest request, @PathVariable("customerId") Integer customerId, @CookieValue(value = SecurityUtil.TOKEN,defaultValue = "") String token){
        List<Hwcase> cases=iHwcase.findListByCustomerId(customerId);
        Result result=new Result(cases,false);
        System.out.println("GET Rest Call: /Hwcase/listByCustomerId/{customerId} ...");
        return result;
    }

    //获取customer对应的case列表
    @RequestMapping(value="/listByCompanyId/{companyId}",method = RequestMethod.GET)
    public Result getListByCompanyId(HttpServletRequest request, @PathVariable("companyId") Integer companyId, @CookieValue(value = SecurityUtil.TOKEN,defaultValue = "") String token){
        List<Hwcase> cases=iHwcase.findListByCompanyId(companyId);
        Result result=new Result(cases,false);
        System.out.println("GET Rest Call: /Hwcase/listByCompanyId/{companyId} ...");
        return result;
    }

    //根据id获取case的detail
    @RequestMapping(value = "/caseDetail/{id}",method = RequestMethod.GET)
    public Result getCaseDetailById(@PathVariable("id") Integer cId,@CookieValue(value = SecurityUtil.TOKEN,defaultValue = "") String token){
        Hwcase hwcase= iHwcase.findById(cId); //加载company信息，customer信息， userId信息
        Result result=new Result(hwcase,false);
        System.out.println("GET Rest Call: /Hwcase/caseDetail/{id} ...");
        return result;
    }

    /**
     * 新增
     */
    @RequestMapping(value="/add",method = RequestMethod.POST)
    public Result addCase(@RequestBody Hwcase theCase,
                          @CookieValue(value = SecurityUtil.TOKEN, defaultValue = "") String token) {
        if (logger.isDebugEnabled()) {
            logger.debug("Rest Call: /Hwcase/add...");
        }
        User currentUser = SecurityUtil.getLoginEmployee(token);
        ResultVo Svo=new ResultVo();
        Svo.setId(-1);
        try{
            Hwcase savedcase = iHwcase.InsertCase(theCase,currentUser);
            Svo.setId(savedcase.getId());
        }catch (Exception e){
            Svo.setEx(e.getMessage());
        }
        Result result = new Result(Svo);
        System.out.println("GET Rest Call: /Hwcase/add ...");
        return result;
    }

    //多条件查询case列表并分页显示
    @RequestMapping(value = "filterPagedCaseByObj",method = RequestMethod.POST)
    public Result filterPagedCases(HttpServletRequest request,@CookieValue(value = SecurityUtil.TOKEN, defaultValue = "") String token,@RequestBody SearchDto caseByObj) throws ParseException {
        if (logger.isDebugEnabled()) {
            logger.debug("Rest Call: /Hwcase/filterPagedCaseByObj...");
        }
        long t1 = new Date().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date createdStartDate = null;
        Date createdEndDate = null;
        Date pendingStartDate = null;
        Date pendingEndDate = null;
        String campaign="-1";
        String status="-1";
        Integer pageNumber=null;
        Integer pageSize=null;

        if(!StringUtils.isEmpty(caseByObj.getCreatedStartDate()) && (!caseByObj.getCreatedStartDate().equals("-1"))){
            createdStartDate = sdf.parse(caseByObj.getCreatedStartDate());
        }
        if(!StringUtils.isEmpty(caseByObj.getCreatedEndDate())&& (!caseByObj.getCreatedEndDate().equals("-1"))){
            createdEndDate = sdf.parse(caseByObj.getCreatedEndDate());
        }
        if(!StringUtils.isEmpty(caseByObj.getPendingStartDate())&& (!caseByObj.getPendingStartDate().equals("-1"))){
            pendingStartDate = sdf.parse(caseByObj.getPendingStartDate());
        }
        if(!StringUtils.isEmpty(caseByObj.getPendingEndDate())&& (!caseByObj.getPendingEndDate().equals("-1"))){
            pendingEndDate = sdf.parse(caseByObj.getPendingEndDate());
        }
        if(!StringUtils.isEmpty(caseByObj.getSaleProjectId())&& (!caseByObj.getSaleProjectId().equals("-1"))){
            campaign = caseByObj.getSaleProjectId();
        }
        if(!StringUtils.isEmpty(caseByObj.getStatus())&& (!caseByObj.getStatus().equals("-1"))){
            status = caseByObj.getStatus();
        }
        User currentUser = SecurityUtil.getLoginEmployee(token);
        pageNumber=caseByObj.getPageNumber();
        if(null == pageNumber){
            pageNumber = caseByObj.getPageNumber();
        }
        if(null == pageSize){
            pageSize = caseByObj.getPageSize();
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ReturnVo> voList=iHwcase.filterPagedCases(createdStartDate,createdEndDate,pendingStartDate,pendingEndDate,campaign,status,currentUser,pageable);
        PagedCase pagedCase=new PagedCase(voList.getContent(),voList.getTotalPages(),voList.getTotalElements(),pageSize,pageNumber);
        caseByObj.setPagedCaseList(pagedCase);
        long t2 = new Date().getTime();
        System.out.println("GET Rest Call: /Hwcase/filterPagedCaseByObj ... "+(t2-t1));
        Result result = new Result(caseByObj,true);
        return result;
    }
}
