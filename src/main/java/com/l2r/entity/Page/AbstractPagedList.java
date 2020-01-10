package com.l2r.entity.Page;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractPagedList<T> implements Serializable {
    List<T> pagedList;
    private int totalPages;
    private long totalElements;
    private int pageSize;
    private int pageNumber;

    public AbstractPagedList() {
    }

    public AbstractPagedList(List<T> pagedList, int totalPages, long totalElements, int pageSize, int pageNumber) {
        this.pagedList = pagedList;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }

    public AbstractPagedList(int totalPages, long totalElements) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public AbstractPagedList(List<T> pagedList, int totalPages, long totalElements) {
        this.pagedList = pagedList;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public List<T> getPagedList() {
        return pagedList;
    }

    public void setPagedList(List<T> pagedList) {
        this.pagedList = pagedList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
