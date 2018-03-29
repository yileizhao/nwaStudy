package com.pantou.cityChain.consts;

/**
 * 原力来源
 */
public enum PowerEnum {
	Register("注册"), Certification("实名认证"), DayLogin("每日登录"), InviteCode("邀请码");
	
	private String value;

	private PowerEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
