package com.l2r.dao;

import com.l2r.entity.Company_tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by messi on 2019-12-20.
 */
@Repository
public interface ICompany_tagsDao extends JpaRepository<Company_tags,String> {
}
