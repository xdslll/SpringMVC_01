package com.demo.model;

public class RespBody <T>{

	private String msg;

	private String code;

	private T data;
	
	private int totalnum;

	private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

    public String getCode() {
		return code;
	}

    public void setCode(String code) {
		this.code = code;
	}

    public T getData() {
		return data;
	}

    public void setData(T data) {
		this.data = data;
	}

    public int getTotalnum() {
		return totalnum;
	}

    public void setTotalnum(int totalnum) {
		this.totalnum = totalnum;
	}
}
