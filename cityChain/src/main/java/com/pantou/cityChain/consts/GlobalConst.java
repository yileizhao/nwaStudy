package com.pantou.cityChain.consts;

import java.util.concurrent.TimeUnit;

/**
 * 全局变量
 */
public class GlobalConst {

	public static String redisMapCoinHavest = "citiChain:map:coinHavest:";
	
	public static int nicknameMax = 20; // 昵称最大长度
	public static int smsCodeLen = 6; // 验证码长度
	public static String smsCodeSource = "0123456789"; // 验证码原串
	public static long smsCodeMin = TimeUnit.MINUTES.toMillis(1); // 登录、注册发送验证码的最小间隔
	public static long smsCodeMax = TimeUnit.MINUTES.toMillis(10); // 验证码失效时间
	public static int inviteCodeLen = 6; // 邀请码长度
	public static String inviteCodeSource = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // 邀请码原串
	public static int inviteCodeMax = 10; // 邀请码最大邀请数量
	public static int inviteCodePower = 10; // 邀请码增加原力值
	public static long idcardMin = TimeUnit.MINUTES.toMillis(1); // 实名认证的最小间隔
	public static int idcardPower = 10; // 实名认证后增加原力
	public static int loginPower = 2; // 每日登录增加原力
	public static int treasureBoxMax = 1; // 每天开宝箱最大次数（最后一个钻石领取后）
	public static int powerInit = 30; // 注册原力初始值
	public static int coinCityTotalPerHour = 100; // 每小时city币投放总量
	public static long baseCityCoinAddTime = TimeUnit.MINUTES.toMillis(48); // city币最后一次活跃后自动增长两天
	public static int baseCityCoinMax = 48; // city币最多未收取48次
	public static int coinHisotoryPageSize = 3; // city币收获记录分页条数
}
