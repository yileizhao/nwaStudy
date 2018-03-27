package com.pantou.cityChain.consts;

import java.util.concurrent.TimeUnit;

public class GlobalConst {

	public static int nicknameMax = 20; // 昵称最大长度
	public static int inviteCodeMax = 10; // 单一邀请码最大邀请数量
	public static long smsCodeMin = TimeUnit.MINUTES.toMicros(1); // 登录、注册发送短信的最小间隔
	public static long idcardMin = TimeUnit.MINUTES.toMicros(1); // 实名认证的最小间隔
	public static int treasureBoxMax = 1; // 每天开宝箱最大次数（最后一个钻石领取后）
	public static int powerInit = 24; // 原力初始值
	public static int coinCityTotalPerHour = 1000; // 每小时city币投放总量
}
