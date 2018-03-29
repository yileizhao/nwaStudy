package com.pantou.cityChain.consts;

/**
 * 币类型
 */
public enum CoinEnum {
	CoinCity("city币"), CoinAtm("代币");

	private String value;

	private CoinEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
