package com.l2r.dao;

import com.l2r.entity.Customer_relation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by messi on 2019-12-20.
 */

@Repository
public interface ICustomer_relationDao extends JpaRepository<Customer_relation,String> {
    @Query(nativeQuery=true,value = "SELECT * from customer_relation where companyId = :companyId and customerId = :customerId")
    Customer_relation FindbyCustomerIdAndCompanyId(@Param("customerId") String customerId, @Param("companyId") String companyId);

    @Query(nativeQuery=true,value = "SELECT * from customer_relation where customerId = :customerId")
    Customer_relation FindbyCustomerId(@Param("customerId") String customerId);
}
