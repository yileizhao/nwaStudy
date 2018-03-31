package com.pantou.cityChain.consts;

/**
 * 币类型
 */
public enum CoinEnum {
	
	CoinCity("city币", "coinCityIcon.png", "city是一种有前景的牛逼币！"), 
	CoinCity1("city币1", "coinCityIcon.png", "city1是一种有前景的牛逼币！"), 
	CoinCity2("city币2", "coinCityIcon.png", "city2是一种有前景的牛逼币！"), 
	CoinCity3("city币3", "coinCityIcon.png", "city3是一种有前景的牛逼币！"), 
	CoinCity4("city币4", "coinCityIcon.png", "city4是一种有前景的牛逼币！"), 
	CoinCity5("city币5", "coinCityIcon.png", "city5是一种有前景的牛逼币！"), 
	CoinCity6("city币6", "coinCityIcon.png", "city6是一种有前景的牛逼币！"), 
	CoinCity7("city币7", "coinCityIcon.png", "city7是一种有前景的牛逼币！"), 
	CoinCity8("city币8", "coinCityIcon.png", "city8是一种有前景的牛逼币！"), 
	CoinCity9("city币9", "coinCityIcon.png", "city9是一种有前景的牛逼币！");

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
