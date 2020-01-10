package com.l2r.dao;


import com.l2r.entity.SerialNum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerialNumRepository extends JpaRepository<SerialNum,String> {
    SerialNum findBySerialYearAndSerialMonthAndSerialDateAndSerialType(Integer year, Integer month, Integer serialDate, SerialNum.SerialTypeEnum type);
}