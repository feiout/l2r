package com.l2r.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 系统流水号
 */
@Entity
@Table(name = "sys_serial_num")
@JsonInclude(Include.NON_NULL)
public class SerialNum implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", columnDefinition = "VARCHAR2|ID", length = 36, nullable = false)
	private String id;

	@Column(name = "SERIAL_NUM", columnDefinition = "NUMBER|流水号", length = 8, nullable = true)
	private Integer serialNum;

	@Column(name = "SERIAL_TYPE", columnDefinition = "NUMBER|流水号类型", length = 3, nullable = true)
	private SerialTypeEnum serialType;

	@Column(name = "SERIAL_DATE", columnDefinition = "NUMBER|日", length = 2, nullable = true)
	private Integer serialDate;

	@Column(name = "SERIAL_YEAR", columnDefinition = "NUMBER|年", length = 4, nullable = true)
	private Integer serialYear;

	@Column(name = "SERIAL_MONTH", columnDefinition = "NUMBER|月", length = 2, nullable = true)
	private Integer serialMonth;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getSerialNum() {
		return this.serialNum;
	}

	public void setSerialNum(Integer serialNum) {
		this.serialNum = serialNum;
	}

	public Integer getSerialDate() {
		return this.serialDate;
	}

	public void setSerialDate(Integer serialDate) {
		this.serialDate = serialDate;
	}

	public Integer getSerialYear() {
		return this.serialYear;
	}

	public void setSerialYear(Integer serialYear) {
		this.serialYear = serialYear;
	}

	public Integer getSerialMonth() {
		return this.serialMonth;
	}

	public void setSerialMonth(Integer serialMonth) {
		this.serialMonth = serialMonth;
	}

	/**
	 * ID
	 */
	public static final String ID = "id";
	/**
	 * 流水号
	 */
	public static final String SERIAL_NUM = "serialNum";
	/**
	 * 流水号类型
	 */
	public static final String SERIAL_TYPE = "serialType";
	/**
	 * 日
	 */
	public static final String SERIAL_DATE = "serialDate";
	/**
	 * 年
	 */
	public static final String SERIAL_YEAR = "serialYear";
	/**
	 * 月
	 */
	public static final String SERIAL_MONTH = "serialMonth";

	public static final String[] All_FIELDS = new String[] { ID, SERIAL_NUM,
			SERIAL_TYPE, SERIAL_DATE, SERIAL_YEAR, SERIAL_MONTH };

	/**
	 * @author zhangwenqiang
	 *
	 */
	public enum SerialTypeEnum {
		CASE_ID,
		LEADS_ID,
		COMPANY_ID,
		CUSTOMER_ID,
		SALEPROJECT_ID,
		USER_ID
	}

	public SerialTypeEnum getSerialType() {
		return serialType;
	}

	public void setSerialType(SerialTypeEnum serialType) {
		this.serialType = serialType;
	}

}
