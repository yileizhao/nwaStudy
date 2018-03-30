package com.pantou.cityChain.consts;

/**
 * 币类型
 */
public enum CoinEnum {
	
	CoinCity("city币", "coinCityIcon.png", "city是一种有前景的牛逼币！"), CoinAtm("代币", "coinAtmIcon.png", "代币是一种伟大的币！");

	private String value;
	private String img;
	private String desc;

	private CoinEnum(String value, String img, String desc) {
		this.value = value;
		this.img = img;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public String getImg() {
		return img;
	}

	public String getDesc() {
		return desc;
	}

}
