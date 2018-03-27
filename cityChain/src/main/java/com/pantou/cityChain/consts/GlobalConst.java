package com.pantou.cityChain.consts;

import java.util.concurrent.TimeUnit;

public class GlobalConst {

	public static String redisMapCoinHavest = "citiChain:map:coinHavest:";
	public static String redisListCoinHistory = "citiChain:list:coinHistory:";
	
	public static int nicknameMax = 20; // 昵称最大长度
	public static int inviteCodeMax = 10; // 单一邀请码最大邀请数量
	public static long smsCodeMin = TimeUnit.MINUTES.toMicros(1); // 登录、注册发送短信的最小间隔
	public static long idcardMin = TimeUnit.MINUTES.toMicros(1); // 实名认证的最小间隔
	public static int treasureBoxMax = 1; // 每天开宝箱最大次数（最后一个钻石领取后）
	public static int powerInit = 24; // 原力初始值
	public static int coinCityTotalPerHour = 100; // 每小时city币投放总量
	public static long baseCityCoinAddTime = TimeUnit.MINUTES.toMicros(48); // city币最后一次活跃后自动增长两天
	public static int baseCityCoinMax = 48; // city币最多未收取48次
}
