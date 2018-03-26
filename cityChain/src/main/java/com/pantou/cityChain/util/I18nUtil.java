package com.pantou.cityChain.util;

import com.pantou.cityChain.vo.TwoTuple;

public class I18nUtil {

	public static TwoTuple<Integer, String> baseSuccess = new TwoTuple<Integer, String>(1, "成功！");
	public static TwoTuple<Integer, String> baseParamError = new TwoTuple<Integer, String>(2, "参数错误！");

	public static TwoTuple<Integer, String> userSmsCodeRegister = new TwoTuple<Integer, String>(3, "验证码注册！");
	public static TwoTuple<Integer, String> userSmsCodeLogin = new TwoTuple<Integer, String>(4, "验证码登录！");

	public static TwoTuple<Integer, String> userRegisterSmsCodeError = new TwoTuple<Integer, String>(5, "注册验证码错误！");
	public static TwoTuple<Integer, String> userRegisterInviteCodeError = new TwoTuple<Integer, String>(6, "注册邀请码错误！");
	public static TwoTuple<Integer, String> userRegisterRegister = new TwoTuple<Integer, String>(7, "注册已注册！");

	public static TwoTuple<Integer, String> userLoginNotRegister = new TwoTuple<Integer, String>(8, "登录未注册！");
	public static TwoTuple<Integer, String> userLoginSmsCodeError = new TwoTuple<Integer, String>(9, "登录验证码错误！");
	public static TwoTuple<Integer, String> userLoginNotCertification = new TwoTuple<Integer, String>(10, "登录实名认证错误！");

	public static TwoTuple<Integer, String> userCertificationNotRegister = new TwoTuple<Integer, String>(11,
			"实名认证未注册！");
	public static TwoTuple<Integer, String> userCertificationCertification = new TwoTuple<Integer, String>(11,
			"实名认证已认证！");
	public static TwoTuple<Integer, String> userCertificationNotCertification = new TwoTuple<Integer, String>(11,
			"实名认证未注册！");
}
