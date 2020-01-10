package com.l2r.exceptions;

import org.springframework.http.HttpStatus;

public class ContactExistException extends ResourceException{

	private static final long serialVersionUID = -754592641316815578L;

	public ContactExistException(){
		this.message = "联系人重复,请查询后处理!";
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
