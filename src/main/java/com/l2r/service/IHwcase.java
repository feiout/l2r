package com.l2r.service;

import com.l2r.base.IService;
import com.l2r.base.Result;
import com.l2r.entity.Hwcase;
import com.l2r.entity.User;
import com.l2r.entity.VO.ReturnVo;
import com.l2r.exceptions.AccountExistException;
import com.l2r.exceptions.ContactExistException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * Created by messi on 2019-12-20.
 */
public interface IHwcase extends IService<Hwcase> {

    List<Hwcase> findListByCustomerId(Integer customerId);

    List<Hwcase> findListByCompanyId(Integer customerId);

    Hwcase findById(Integer cId);

    Hwcase InsertCase(Hwcase theCase, User currentUser) throws AccountExistException,ContactExistException;

    Page<ReturnVo> filterPagedCases(Date createdStartDate, Date createdEndDate, Date pendingStartDate, Date pendingEndDate, String saleProjectId, String status, User currentUser, Pageable pageable);

}
