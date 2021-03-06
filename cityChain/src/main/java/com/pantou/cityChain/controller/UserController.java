package com.pantou.cityChain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.pantou.cityChain.consts.CoinEnum;
import com.pantou.cityChain.consts.GlobalConst;
import com.pantou.cityChain.consts.LangConst;
import com.pantou.cityChain.consts.PowerEnum;
import com.pantou.cityChain.entity.PowerHistoryEntity;
import com.pantou.cityChain.entity.UserEntity;
import com.pantou.cityChain.repository.PowerHistoryRepository;
import com.pantou.cityChain.repository.UserRepository;
import com.pantou.cityChain.service.UserService;
import com.pantou.cityChain.util.GlobalUtil;
import com.pantou.cityChain.util.TimeUtil;
import com.pantou.cityChain.util.ValidateUtil;
import com.pantou.cityChain.vo.JsonBase;

/*
 * 用户控制器：@RestController 等于 @Controller @ResponseBody的混合体
 */
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PowerHistoryRepository powerHistoryRepository;

	/*
	 * 注册机器人
	 */
	@RequestMapping("/user/robot")
	public JsonBase smsCode(@RequestParam(name = "cnt", required = false, defaultValue = "1") int cnt) {
		String urlPrefix = "localhost:82";
		int maxCnt = 10000;
		cnt = Math.min(cnt, maxCnt);
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < cnt; i++) {
			int endfix = maxCnt + i;
			try {
				String mobile = "177253" + endfix;
				System.out.println(
						HttpClientUtil.get(HttpConfig.custom().url("http://" + urlPrefix + "/user/smsCode?mobile=" + mobile)));
				Thread.sleep(10);

				System.out.println(HttpClientUtil.get(HttpConfig.custom().url("http://" + urlPrefix + "/user/register?mobile="
						+ mobile + "&smsCode=123456&nickname=zyl" + i + "&inviteCode=")));
				Thread.sleep(10);

				String str = HttpClientUtil.get(
						HttpConfig.custom().url("http://" + urlPrefix + "/user/login?mobile=" + mobile + "&smsCode=123456"));
				System.out.println(str);
				String token = JSONObject.parseObject(str).getString("object");
				Thread.sleep(10);

				str = HttpClientUtil.get(HttpConfig.custom().url("http://" + urlPrefix + "/user/certification?token=" + token
						+ "&name=赵亦磊" + endfix + "&idcard=13053519851014" + endfix));
				System.out.println(str);
				Thread.sleep(10);

				str = HttpClientUtil.get(HttpConfig.custom().url("http://" + urlPrefix + "/base/main?token=" + token));
				System.out.println(str);
				Thread.sleep(10);

				JSONObject jsonObjectCoins = JSONObject.parseObject(str).getJSONObject("object").getJSONObject("coins");
				if (jsonObjectCoins != null) {
					for (String coinKey : jsonObjectCoins.keySet()) {
						System.out.println(HttpClientUtil.get(HttpConfig.custom()
								.url("http://" + urlPrefix + "/base/harvest?token=" + token + "&coinKey=" + coinKey)));
					}
				}
				Thread.sleep(10);

				System.out.println(HttpClientUtil.get(HttpConfig.custom()
						.url("http://" + urlPrefix + "/base/history?token=" + token + "&coin=0&type=0&plusMinus=0&page=0")));
				Thread.sleep(10);

				System.out.println(HttpClientUtil.get(HttpConfig.custom()
						.url("http://" + urlPrefix + "/base/powerHistory?token=" + token + "&power=0&page=0")));
				Thread.sleep(10);

				jsonObject.put("token", token);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		JsonBase jsonBase = new JsonBase();
		jsonBase.init(LangConst.baseSuccess);
		jsonBase.setObject(jsonObject);

		return jsonBase;
	}

	/*
	 * 验证码（注册、登录）
	 */
	@RequestMapping("/user/smsCode")
	public JsonBase smsCode(@RequestParam String mobile) {
		JsonBase jsonBase = new JsonBase();
		if (!StringUtils.isEmpty(mobile) && ValidateUtil.isMobile(mobile)) {
			UserEntity userEntity = userRepository.findByMobile(mobile);
			if (userEntity == null || StringUtils.isEmpty(userEntity.getNickname())) { // 注册
				if (userEntity == null) {
					userEntity = new UserEntity();
					userEntity.setMobile(mobile);
				}
				jsonBase.init(LangConst.userSmsCodeRegister);
			} else { // 登录
				jsonBase.init(LangConst.userSmsCodeLogin);
			}
			long diffTime = GlobalConst.smsCodeMin - TimeUtil.diff(userEntity.getTimeSmsCode(), TimeUtil.now());
			if (diffTime > 0) { // 验证码时间错误
				jsonBase.init(LangConst.userSmsSmsCodeTimeError);
				jsonBase.setObject(diffTime);
			} else { // 有效请求
				// TODO 发送短信验证码
				userEntity.setSmsCode(userService.produceSmsCode());
				userEntity.setTimeSmsCode(TimeUtil.now());
				userRepository.save(userEntity);
			}
		} else {
			jsonBase.init(LangConst.baseParamError);
		}

		return jsonBase;
	}

	/*
	 * 注册
	 */
	@RequestMapping("/user/register")
	public JsonBase register(@RequestParam String mobile, @RequestParam String smsCode, @RequestParam String inviteCode,
			@RequestParam String nickname) {
		JsonBase jsonBase = new JsonBase();
		if (!StringUtils.isEmpty(mobile) && !StringUtils.isEmpty(smsCode) && !StringUtils.isEmpty(nickname)
				&& ValidateUtil.isMobile(mobile) && ValidateUtil.isNickName(nickname)) {
			UserEntity userEntity = userRepository.findByMobile(mobile);
			if (userEntity == null || StringUtils.isEmpty(userEntity.getNickname())) { // 未注册
				if (userEntity == null) {
					userEntity = new UserEntity();
					userEntity.setMobile(mobile);
				}
				if (!smsCode.equals(userEntity.getSmsCode())
						|| TimeUtil.now() > userEntity.getTimeSmsCode() + GlobalConst.smsCodeMax) { // 验证码错误
					jsonBase.init(LangConst.userRegisterSmsCodeError);
				} else {
					UserEntity userEntityNickname = userRepository.findByNickname(nickname);
					if (userEntityNickname != null) {
						jsonBase.init(LangConst.userRegisterNicknameExist);
					} else { // 有效请求
						for (CoinEnum coinEnum : CoinEnum.values()) {
							userEntity.addCoin(coinEnum, 0);
						}
						userEntity.setInviteCode(userService.produceInviteCode());
						userEntity.setNickname(nickname);
						userEntity.setPower(GlobalConst.powerInit);
						long now = TimeUtil.now();
						powerHistoryRepository.save(new PowerHistoryEntity(userEntity.getId(), now,
								GlobalConst.powerInit, PowerEnum.Register));
						String token = GlobalUtil.getUuid();
						userEntity.setToken(token);
						userRepository.save(userEntity);

						// 邀请人增加原力
						if (!StringUtils.isEmpty(inviteCode)) {
							UserEntity userEntityInviteCode = userRepository.findByInviteCode(inviteCode);
							if (userEntityInviteCode != null) {
								int inviteCodeCnt = userEntityInviteCode.getInviteCodeCnt();
								if (inviteCodeCnt < GlobalConst.inviteCodeMax) {
									userEntityInviteCode.setInviteCodeCnt(inviteCodeCnt + 1);
									userEntityInviteCode
											.setPower(userEntityInviteCode.getPower() + GlobalConst.inviteCodePower);
									userRepository.save(userEntityInviteCode);
									powerHistoryRepository.save(new PowerHistoryEntity(userEntityInviteCode.getId(),
											now, GlobalConst.powerInit, PowerEnum.Register));
								}
							}
						}
						jsonBase.init(LangConst.baseSuccess);
						jsonBase.setObject(token);
					}
				}
			} else { // 已注册
				jsonBase.init(LangConst.userRegisterRegister);
			}
		} else { // 参数错误
			jsonBase.init(LangConst.baseParamError);
		}

		return jsonBase;
	}

	/*
	 * 登录
	 */
	@RequestMapping("/user/login")
	public JsonBase login(@RequestParam String mobile, @RequestParam String smsCode) {
		JsonBase jsonBase = new JsonBase();
		if (!StringUtils.isEmpty(mobile) && !StringUtils.isEmpty(smsCode) && ValidateUtil.isMobile(mobile)) {
			UserEntity userEntity = userRepository.findByMobile(mobile);
			if (userEntity == null || StringUtils.isEmpty(userEntity.getNickname())) { // 未注册
				jsonBase.init(LangConst.userLoginNotRegister);
			} else if (!smsCode.equals(userEntity.getSmsCode())
					|| TimeUtil.now() > userEntity.getTimeSmsCode() + GlobalConst.smsCodeMax) { // 验证码错误
				jsonBase.init(LangConst.userLoginSmsCodeError);
			} else { // 有效请求
				String token = GlobalUtil.getUuid();
				userEntity.setToken(token);
				userRepository.save(userEntity);
				jsonBase.init(LangConst.baseSuccess);
				jsonBase.setObject(token);
			}
		} else { // 参数错误
			jsonBase.init(LangConst.baseParamError);
		}

		return jsonBase;
	}

	/*
	 * 实名认证：暂时不用验证，无法避免，若不能更改信息则可考虑在注册或登录成功特定时间内才可以实名认证，防止其他用户恶意变更
	 */
	@RequestMapping("/user/certification")
	public JsonBase certification(@RequestParam String token, @RequestParam String name, @RequestParam String idcard) {
		JsonBase jsonBase = new JsonBase();
		if (!StringUtils.isEmpty(token) && !StringUtils.isEmpty(name) && !StringUtils.isEmpty(idcard)) {
			UserEntity userEntity = userRepository.findByToken(token);
			if (userEntity == null || StringUtils.isEmpty(userEntity.getNickname())) { // 实名认证未注册
				jsonBase.init(LangConst.userCertificationNotRegister);
			} else if (!StringUtils.isEmpty(userEntity.getName())) { // 实名认证已认证
				jsonBase.init(LangConst.userCertificationCertification);
			} else if (TimeUtil.diff(userEntity.getTimeIdcard(), TimeUtil.now()) < GlobalConst.idcardMin) { // 实名认证时间错误
				jsonBase.init(LangConst.userCertificationCertification);
			} else if (Math.random() > 1.0) { // TODO 实名认证错误
				jsonBase.init(LangConst.userCertificationNotCertification);
			} else { // 有效请求
				userEntity.setName(name);
				userEntity.setIdcard(idcard);
				userEntity.setPower(userEntity.getPower() + GlobalConst.idcardPower);
				userRepository.save(userEntity);
				powerHistoryRepository.save(new PowerHistoryEntity(userEntity.getId(), TimeUtil.now(),
						GlobalConst.idcardPower, PowerEnum.Certification));
				jsonBase.init(LangConst.baseSuccess);
			}
		} else { // 参数错误
			jsonBase.init(LangConst.baseParamError);
		}

		return jsonBase;
	}
}
