package com.l2r.service;


import com.l2r.base.IService;
import com.l2r.entity.SerialNum;

public interface ISerialNum extends IService<SerialNum> {

	String getSerialNum(SerialNum.SerialTypeEnum type, String prefix);
}
