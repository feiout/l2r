package com.l2r.dao;

import com.l2r.entity.User_logs;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by messi on 2020-01-03.
 */
public interface IUser_logsDao extends JpaRepository<User_logs,String> {
}
