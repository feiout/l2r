package com.l2r.dao;

import com.l2r.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Messi on 2019/11/18.
 */

@Repository
public interface ICustomerDao extends JpaRepository<Customer,String> {

    @Query(nativeQuery=true,value = "SELECT * from customer where name = :name and email =:email and cellphone =:cellphone AND workphone =:workphone")
    List<Customer> FindByNameAndEmailAndCellphoneAndWorkphone(@Param("name") String name, @Param("email") String email, @Param("cellphone") String cellphone, @Param("workphone") String workphone);

    @Query(nativeQuery=true,value = "SELECT * from customer where id= :id")
    Customer FindById(@Param("id") Integer id);

    @Query(nativeQuery=true,value = "SELECT * from customer where name = :name and email =:email and cellphone =:cellphone AND workphone =:workphone and id= :id")
    List<Customer> FindByNameAndEmailAndCellphoneAndWorkphoneAndId(@Param("name") String name, @Param("email") String email, @Param("cellphone") String cellphone, @Param("workphone") String workphone, @Param("id") Integer id);

    @Query(nativeQuery=true,value = "SELECT * from customer where customerId in (SELECT customerId from customer_relation where companyId=:companyId)")
    List<Customer> FindByCompanyId(@Param("companyId") String companyId);
}
