package com.demo.exception;


import com.demo.util.ErrorMsg;

public class BusinessException extends RuntimeException {
	
	private String code;
	
	private String msg;

	public BusinessException(String code)
	{
		this.code = code;
		this.msg = ErrorMsg.getProperty(code);
	}

	public BusinessException(String code, String msg)
	{
		this.code = code;
		this.msg = msg;
		
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String toString(){
		return "errorcode:" + code + ",errormsg:" + msg;
	}
	

}
