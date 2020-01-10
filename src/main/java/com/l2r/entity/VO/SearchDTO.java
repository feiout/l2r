package com.l2r.entity.VO;

import com.l2r.entity.Page.PagedCase;

import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by messi on 2020-01-03.
 * Pass parameters to Server side.
 */
public class SearchDto {
    private String saleId;
    private String type;
    private String source;
    private String status;
    private String saleProjectId;
    private String callType;   //0,inbound call; 1,outbound call
    private String subscriptionId;
    private String userId;
    private String createdStartDate;
    private String createdEndDate;
    private String pendingStartDate;
    private String pendingEndDate;
    private PagedCase PagedCaseList;
    private Integer pageNumber;
    private Integer pageSize;

//    public SearchCaseDTO(String saleProjectId, String createdStartDate, String createdEndDate, String pendingStartDate, String pendingEndDate, String status) {
//        this.saleProjectId=saleProjectId;
//        this.createdStartDate=createdStartDate;
//        this.createdEndDate=createdEndDate;
//        this.pendingStartDate=pendingStartDate;
//        this.pendingEndDate=pendingEndDate;
//        this.status=status;
//    }


    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSaleProjectId() {
        return saleProjectId;
    }

    public void setSaleProjectId(String saleProjectId) {
        this.saleProjectId = saleProjectId;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedStartDate() {
        return createdStartDate;
    }

    public void setCreatedStartDate(String createdStartDate) {
        this.createdStartDate = createdStartDate;
    }

    public String getCreatedEndDate() {
        return createdEndDate;
    }

    public void setCreatedEndDate(String createdEndDate) {
        this.createdEndDate = createdEndDate;
    }

    public String getPendingStartDate() {
        return pendingStartDate;
    }

    public void setPendingStartDate(String pendingStartDate) {
        this.pendingStartDate = pendingStartDate;
    }

    public String getPendingEndDate() {
        return pendingEndDate;
    }

    public void setPendingEndDate(String pendingEndDate) {
        this.pendingEndDate = pendingEndDate;
    }

    public PagedCase getPagedCaseList() {
        return PagedCaseList;
    }

    public void setPagedCaseList(PagedCase pagedCaseList) {
        PagedCaseList = pagedCaseList;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
