package com.pantou.cityChain.util;

import java.util.Random;
import java.util.UUID;

public class GlobalUtil {
	
	public static Random random = new Random();

	/*
	 * 获取uuid
	 */
	public static String getUuid() {
		return UUID.randomUUID().toString();
	}
}
