package com.l2r.dao;

import com.l2r.entity.Customer_logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by messi on 2019-12-20.
 */

@Repository
public interface ICustomer_logsDao extends JpaRepository<Customer_logs,String> {
}
