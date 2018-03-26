package com.pantou.cityChain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pantou.cityChain.entity.UserEntity;
import com.pantou.cityChain.repository.UserRepository;
import com.pantou.cityChain.service.UserService;
import com.pantou.cityChain.util.ConstUtil;
import com.pantou.cityChain.util.GlobalUtil;
import com.pantou.cityChain.util.I18nUtil;
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
				jsonBase.init(I18nUtil.userSmsCodeRegister);
			} else { // 登录
				jsonBase.init(I18nUtil.userSmsCodeLogin);
			}
			// TODO 发送短信验证码
			userEntity.setSmsCode("ABCDEF");
			userEntity.setSmsCodeTime(GlobalUtil.now());
			userRepository.save(userEntity);
		} else {
			jsonBase.init(I18nUtil.baseParamError);
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
					jsonBase.init(I18nUtil.userRegisterSmsCodeError);
				} else if (userEntityInviteCode == null
						|| userEntityInviteCode.getInviteCodeCnt() > ConstUtil.inviteCodeMax) { // 邀请码错误
					jsonBase.init(I18nUtil.userRegisterInviteCodeError);
				} else { // 注册成功
					userEntity.setNickname(nickname);
					userEntity.setInviteCode(inviteCode);
					userRepository.save(userEntity);
					jsonBase.init(I18nUtil.baseSuccess);
				}
			} else { // 已注册
				jsonBase.init(I18nUtil.userRegisterRegister);
			}
		} else { // 参数错误
			jsonBase.init(I18nUtil.baseParamError);
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
				jsonBase.init(I18nUtil.userLoginNotRegister);
			} else if (!smsCode.equals(userEntity.getSmsCode())) { // 验证码错误
				jsonBase.init(I18nUtil.userLoginSmsCodeError);
			} else if (StringUtils.isEmpty(userEntity.getName())) { // 实名认证错误
				jsonBase.init(I18nUtil.userLoginNotCertification);
			} else { // 登录成功
				userService.doLogin(jsonBase, userEntity);
			}
		} else { // 参数错误
			jsonBase.init(I18nUtil.baseParamError);
		}

		return jsonBase;
	}

	/*
	 * 实名认证：暂时不用验证，无法避免，若不能更改信息则可考虑在注册或登录成功特定时间内才可以实名认证，防止其他用户恶意变更
	 */
	@RequestMapping("/user/certification")
	public JsonBase certification(@RequestParam String mobile, @RequestParam String name, @RequestParam String idcard) {
		JsonBase jsonBase = new JsonBase();
		if (!StringUtils.isEmpty(mobile) && !StringUtils.isEmpty(name) && !StringUtils.isEmpty(idcard)
				&& ValidateUtil.isMobile(mobile)) {
			UserEntity userEntity = userRepository.findByMobile(mobile);
			if (userEntity == null || StringUtils.isEmpty(userEntity.getNickname())) { // 实名认证未注册
				jsonBase.init(I18nUtil.userCertificationNotRegister);
			} else if (!StringUtils.isEmpty(userEntity.getName())) { // 实名认证已认证
				jsonBase.init(I18nUtil.userCertificationCertification);
			} else if (false) { // TODO 实名认证错误
				jsonBase.init(I18nUtil.userCertificationNotCertification);
			} else { // 实名认证成功
				userEntity.setName(name);
				userEntity.setIdcard(idcard);
				userRepository.save(userEntity);
				userService.doLogin(jsonBase, userEntity);
			}
		} else { // 参数错误
			jsonBase.init(I18nUtil.baseParamError);
		}

		return jsonBase;
	}
}
