package com.l2r.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.l2r.utils.ObjectUtil;
import com.l2r.utils.StdDateFormat;
import com.l2r.utils.ZipUtils;
import org.springframework.data.domain.Page;

import java.io.UnsupportedEncodingException;


@JsonInclude(Include.NON_NULL)
public class Result {

	public Result() {
		this.isZipData = false;
	}

	public Result(Boolean zipped)
	{
		this.isZipData = zipped;
	}

	public Result(Object data) {
		this.isZipData = false;
		handleData(data);
	}

	public Result(Object data,Boolean zipped) {
		this.isZipData = zipped;
		handleData(data);
	}

	@SuppressWarnings("rawtypes")
	private void handleData(Object data) {
		try {
			ObjectMapper jsonMapper = new ObjectMapper();
			jsonMapper.setDateFormat(new StdDateFormat());
			jsonMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			jsonMapper.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
			jsonMapper.getDeserializationConfig().getDateFormat();

			jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			if (data instanceof Page) {
				if(this.isZipData)
				{
					this.data = ZipUtils.gZip(jsonMapper.writeValueAsString(((Page) data).getContent()).getBytes("UTF-8"));
				}
				else
				{
					this.data = jsonMapper.writeValueAsString(((Page) data).getContent());
				}
				this.page = (Page) data;
			} else {
				if(!ObjectUtil.isNullOrEmpty(isZipData) && this.isZipData)
				{
					this.data = ZipUtils.gZip(jsonMapper.writeValueAsString(data).getBytes("UTF-8"));
				}
				else
				{
					this.data = jsonMapper.writeValueAsString(data);
				}
			}
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private Object data;

	private Page page;

	private Integer code;

	private String message;

	private String token;

	public Boolean getIsZipData()
	{
		return isZipData;
	}

	public void setIsZipData(Boolean isZipData)
	{
		this.isZipData = isZipData;
	}

	private Boolean isZipData;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		handleData(data);
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}