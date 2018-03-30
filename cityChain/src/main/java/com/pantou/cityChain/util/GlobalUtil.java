package com.pantou.cityChain.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.pantou.cityChain.consts.CoinEnum;

/**
 * 全局工具类
 */
public class GlobalUtil {

	/**
	 * 随机器
	 */
	public static Random random = new Random();

	/**
	 * 获取uuid
	 */
	public static String getUuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 根据索引获取
	 */
	public static <T extends Enum<T>> T valueOf(Class<T> clazz, int ordinal) {
		return (T) clazz.getEnumConstants()[ordinal];
	}

	/*
	 * 格式化coins
	 */
	public static Map<CoinEnum, Double> formateCoins(JSONObject coins) {
		Map<CoinEnum, Double> result = new HashMap<CoinEnum, Double>();
		if (coins != null) {
			for (String key : coins.keySet()) {
				result.put(CoinEnum.valueOf(key), coins.getDoubleValue(key));
			}
		}

		return result;
	}

}
