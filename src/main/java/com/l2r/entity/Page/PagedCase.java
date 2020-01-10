package com.l2r.entity.Page;

import com.l2r.base.AbstractService;
import com.l2r.entity.VO.ReturnVo;

import java.util.List;

/**
 * Created by messi on 2020-01-03.
 */
public class PagedCase extends AbstractPagedList<ReturnVo> {
    public PagedCase(List<ReturnVo> pagedList, int totalPages, long totalElements, int pageSize, int pageNumber){
        super(pagedList,totalPages,totalElements,pageSize,pageNumber);
    }
}
