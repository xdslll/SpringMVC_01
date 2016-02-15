package com.demo.model;

import java.util.List;

public class RespBody <T>{

	private String msg;

	private String code;

	private T data;

	private List<T> datas;
	
	private int totalnum;

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

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
}
