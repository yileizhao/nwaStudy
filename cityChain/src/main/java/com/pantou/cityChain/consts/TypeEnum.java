package com.pantou.cityChain.consts;

/**
 * 币来源
 */
public enum TypeEnum {
	
	Reward("奖励", "rewardIcon.png"), Treasure("宝箱", "treasureIcon.png");

	private String value;
	private String img;

	private TypeEnum(String value, String img) {
		this.value = value;
		this.img = img;
	}

	public String getValue() {
		return value;
	}

	public String getImg() {
		return img;
	}

}
