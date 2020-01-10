package com.l2r.dao;

import com.l2r.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by messi on 2020-01-01.
 */
public interface IProductDao extends JpaRepository<Product,String> {

    @Query(nativeQuery=true,value="SELECT * FROM product")
    List<Product> QueryALL();

    @Query(nativeQuery=true,value="SELECT id,name,price,category FROM product")
    List<Object[]> FindAllByVos();
}
