package com.pantou.cityChain.consts;

/**
 * 币来源
 */
public enum TypeEnum {
	Reward("奖励"), Treasure("宝箱");
	
	private String value;

	private TypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
