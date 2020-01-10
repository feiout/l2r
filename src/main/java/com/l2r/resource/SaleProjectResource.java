package com.l2r.resource;

import com.l2r.base.BaseResource;
import com.l2r.base.Result;
import com.l2r.entity.SaleProject;
import com.l2r.service.ISaleProject;
import com.l2r.service.IUserlogin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by messi on 2019-12-20.
 */
@RestController
@RequestMapping("/SaleProject")
public class SaleProjectResource extends BaseResource {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ISaleProject saleProject;


    @RequestMapping(method = RequestMethod.GET,value = "/list")
    public Result getSaleProjectList(HttpServletResponse response) {
        if(logger.isDebugEnabled()){
            logger.debug("Rest Call: /SaleProject/list ...");
        }
        List<SaleProject> saleProjectList=saleProject.findAll();
        Result result=new Result(saleProjectList,false);
        System.out.println("GET Rest Call: /SaleProject/list ...");
        return result;
    }

}
