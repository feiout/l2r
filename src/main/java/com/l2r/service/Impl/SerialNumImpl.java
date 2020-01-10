package com.l2r.service.Impl;


import com.l2r.base.AbstractService;
import com.l2r.dao.SerialNumRepository;
import com.l2r.entity.SerialNum;
import com.l2r.service.ISerialNum;
import com.l2r.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class SerialNumImpl extends AbstractService<SerialNum> implements ISerialNum {

	@Autowired
	private SerialNumRepository serialNumDao;

	@Override
	public synchronized String getSerialNum(SerialNum.SerialTypeEnum type, String prefix) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int date = calendar.get(Calendar.DATE);
		SerialNum serialNum = serialNumDao.findBySerialYearAndSerialMonthAndSerialDateAndSerialType(year,month,date,type);
		if (serialNum != null) {
			serialNum.setSerialNum(serialNum.getSerialNum() + 1);
			serialNumDao.save(serialNum);
		} else {
			serialNum = new SerialNum();
			serialNum.setId(UUIDUtil.getUUID36Bits());
			serialNum.setSerialYear(year);
			serialNum.setSerialMonth(month);
			serialNum.setSerialDate(date);
			serialNum.setSerialType(type);
			int s = 10000;
			// 默认从10000开始，以免需要补零来填充5位
			serialNum.setSerialNum(s);
			serialNumDao.save(serialNum);
		}
		String m = month >= 10 ? serialNum.getSerialMonth().toString() : "0" + serialNum.getSerialMonth().toString();
		String d = date >= 10 ? serialNum.getSerialDate().toString() : "0" + serialNum.getSerialDate().toString();
		String result = prefix + serialNum.getSerialYear().toString() + m + d + serialNum.getSerialNum().toString();
		return result;
	}

	@Override
	public JpaRepository<SerialNum, String> getRepository() {
		return serialNumDao;
	}
}
