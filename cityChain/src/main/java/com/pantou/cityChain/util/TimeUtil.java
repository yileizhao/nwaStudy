package com.pantou.cityChain.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 */
public class TimeUtil {

	public static SimpleDateFormat sdfYmdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat sdfYmd = new SimpleDateFormat("yyyy-MM-dd");
	public static long minute = 60 * 1000;// 1分钟
	public static long hour = 60 * minute;// 1小时
	public static long day = 24 * hour;// 1天
	public static long month = 31 * day;// 月
	public static long year = 12 * month;// 年

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

	/*
	 * 
	 */
	public static String formatText(long time) {
		long diff = now() - time;
		long r = 0;
		if (diff > year) {
			r = (diff / year);
			return r + "年前";
		} else if (diff > month) {
			r = (diff / month);
			return r + "月前";
		} else if (diff > day * 2) {
			r = (diff / day);
			return r + "天前";
		} else if (diff > day) {
			r = (diff / day);
			return r + "昨天";
		} else if (diff > hour) {
			r = (diff / hour);
			return r + "小时前";
		} else if (diff > minute) {
			r = (diff / minute);
			return r + "分钟前";
		}
		return diff / 1000 + "秒前";
	}
}
