package com.pantou.cityChain.consts;

import com.pantou.cityChain.vo.TwoTuple;

public class LangConst {

	public static TwoTuple<Integer, String> baseSuccess = new TwoTuple<Integer, String>(10000, "成功！");
	public static TwoTuple<Integer, String> baseParamError = new TwoTuple<Integer, String>(10010, "参数错误！");
	public static TwoTuple<Integer, String> baseToken = new TwoTuple<Integer, String>(10020, "令牌错误！");

	public static TwoTuple<Integer, String> userSmsCodeRegister = new TwoTuple<Integer, String>(10100, "验证码注册！");
	public static TwoTuple<Integer, String> userSmsCodeLogin = new TwoTuple<Integer, String>(10110, "验证码登录！");
	public static TwoTuple<Integer, String> userSmsSmsCodeTimeError = new TwoTuple<Integer, String>(10120, "验证码时间错误！");

	public static TwoTuple<Integer, String> userRegisterSmsCodeError = new TwoTuple<Integer, String>(10200, "注册验证码错误！");
	public static TwoTuple<Integer, String> userRegisterRegister = new TwoTuple<Integer, String>(10210, "注册已注册！");
	public static TwoTuple<Integer, String> userRegisterNicknameExist = new TwoTuple<Integer, String>(10220, "注册昵称已存在！");

	public static TwoTuple<Integer, String> userLoginNotRegister = new TwoTuple<Integer, String>(10300, "登录未注册！");
	public static TwoTuple<Integer, String> userLoginSmsCodeError = new TwoTuple<Integer, String>(10310, "登录验证码错误！");

	public static TwoTuple<Integer, String> userCertificationNotRegister = new TwoTuple<Integer, String>(10400, "实名认证未注册！");
	public static TwoTuple<Integer, String> userCertificationCertification = new TwoTuple<Integer, String>(10410, "实名认证已认证！");
	public static TwoTuple<Integer, String> userCertificationIdcardTimeError = new TwoTuple<Integer, String>(10420, "实名认证时间错误！");
	public static TwoTuple<Integer, String> userCertificationNotCertification = new TwoTuple<Integer, String>(10430, "实名认证错误！");
	
	public static TwoTuple<Integer, String> baseHavestNotExisit = new TwoTuple<Integer, String>(10500, "收获不存在！");
	
	
}
