package com.l2r.exceptions;

import org.springframework.http.HttpStatus;

public class AccountExistException extends ResourceException{

	private static final long serialVersionUID = -754592641316815578L;

	public AccountExistException(){
		this.message = "公司名重复,请查询后处理!";
	}
	
	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.NOT_ACCEPTABLE;
	}

	@Override
	public int getCode() {
		return 10001;
	}
}
