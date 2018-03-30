package com.pantou.cityChain.util;

import java.util.Random;
import java.util.UUID;

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

}
