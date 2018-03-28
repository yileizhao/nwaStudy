package com.pantou.cityChain.vo;

/**
 * 接口返回基础信息
 */
public class JsonBase {

	private int code; // 返回码
	private String msg; // 返回信息
	private Object object; // 对象信息
	
	public void init(TwoTuple<Integer, String> twoTuple) {
		this.code = twoTuple.first;
		this.msg = twoTuple.second;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
}
