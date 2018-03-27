package com.pantou.cityChain.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.pantou.cityChain.consts.GlobalConst;
import com.pantou.cityChain.consts.LangConst;
import com.pantou.cityChain.entity.UserEntity;
import com.pantou.cityChain.repository.UserRepository;
import com.pantou.cityChain.service.UserService;
import com.pantou.cityChain.util.GlobalUtil;
import com.pantou.cityChain.util.TimeUtil;
import com.pantou.cityChain.util.ValidateUtil;
import com.pantou.cityChain.vo.JsonBase;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

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
			if (TimeUtil.diff(userEntity.getTimeSmsCode(), TimeUtil.now()) < GlobalConst.smsCodeMin) { // 验证码时间错误
				jsonBase.init(LangConst.userSmsSmsCodeTimeError);
			} else {
				// TODO 发送短信验证码
				userEntity.setSmsCode("ABCDEF");
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
		if (!StringUtils.isEmpty(mobile) && !StringUtils.isEmpty(smsCode) && !StringUtils.isEmpty(mobile)
				&& !StringUtils.isEmpty(smsCode) && ValidateUtil.isMobile(mobile)
				&& ValidateUtil.isNickName(nickname)) {
			UserEntity userEntity = userRepository.findByMobile(mobile);
			if (userEntity == null || StringUtils.isEmpty(userEntity.getNickname())) { // 未注册
				UserEntity userEntityInviteCode = userRepository.findByInviteCode(inviteCode);
				if (!smsCode.equals(userEntity.getSmsCode())) { // 验证码错误
					jsonBase.init(LangConst.userRegisterSmsCodeError);
				} else if (userEntityInviteCode == null
						|| userEntityInviteCode.getInviteCodeCnt() > GlobalConst.inviteCodeMax) { // 邀请码错误
					jsonBase.init(LangConst.userRegisterInviteCodeError);
				} else { // 注册成功
					userEntity.setInviteCode(inviteCode);
					userEntity.setNickname(nickname);
					userEntity.setPower(GlobalConst.powerInit);
					userEntity.setToken(GlobalUtil.getUuid());
					userRepository.save(userEntity);
					jsonBase.init(LangConst.baseSuccess);
					jsonBase.setObject(userEntity);
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
			} else if (!smsCode.equals(userEntity.getSmsCode())) { // 验证码错误
				jsonBase.init(LangConst.userLoginSmsCodeError);
			} else if (StringUtils.isEmpty(userEntity.getName())) { // 实名认证错误
				jsonBase.init(LangConst.userLoginNotCertification);
			} else { // 登录成功
				userEntity.setToken(GlobalUtil.getUuid());
				userRepository.save(userEntity);
				userService.doLogin(jsonBase, userEntity);
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
			} else if (Math.random() > 0.5) { // TODO 实名认证错误
				jsonBase.init(LangConst.userCertificationNotCertification);
			} else { // 实名认证成功
				userEntity.setName(name);
				userEntity.setIdcard(idcard);
				userRepository.save(userEntity);
				userService.doLogin(jsonBase, userEntity);
			}
		} else { // 参数错误
			jsonBase.init(LangConst.baseParamError);
		}

		return jsonBase;
	}

	/*
	 * 基地
	 */
	@RequestMapping("/user/base")
	public JsonBase base(@RequestParam String token) {
		JsonBase jsonBase = new JsonBase();
		if (!StringUtils.isEmpty(token)) {
			UserEntity userEntity = userRepository.findByToken(token);
			if (userEntity == null || StringUtils.isEmpty(userEntity.getName())) { // 基地未实名认证
				jsonBase.init(LangConst.userBaseNotCertification);
			} else { // 有效请求
				long now = TimeUtil.now();
				if(!TimeUtil.isSameDay(userEntity.getTimeBase(), now)) { // 每日登录，增加原力
					userEntity.setTimeBase(now);
					userRepository.save(userEntity);
				}
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("nextRefTime", 60 - Integer.parseInt(TimeUtil.sdfYmdhms.format(new Date(now)).substring(17)));
				jsonBase.setObject(jsonObject);
			}
		} else { // 参数错误
			jsonBase.init(LangConst.baseParamError);
		}

		return jsonBase;
	}
}
