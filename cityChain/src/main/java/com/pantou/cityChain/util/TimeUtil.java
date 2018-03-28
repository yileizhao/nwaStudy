package com.pantou.cityChain.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 */
public class TimeUtil {

	public static SimpleDateFormat sdfYmdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat sdfYmd = new SimpleDateFormat("yyyy-MM-dd");

	/*
	 * 获取当前时间戳
	 */
	public static long now() {
		return System.currentTimeMillis();
	}

	/*
	 * 获取时间差
	 */
	public static long diff(long time1, long time2) {
		return time2 - time1;
	}

	/*
	 * 是否同一天
	 */
	public static boolean isSameDay(long time1, long time2) {
		return sdfYmd.format(new Date(time1)).equals(sdfYmd.format(new Date(time2)));
	}
}
