package com.pantou.cityChain.consts;

/**
 * 币增减
 */
public enum PlusMinusEnum {
	Plus("收获"), Minus("消耗");
	
	private String value;

	private PlusMinusEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
