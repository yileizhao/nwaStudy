package com.pantou.cityChain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pantou.cityChain.consts.GlobalConst;
import com.pantou.cityChain.repository.UserRepository;
import com.pantou.cityChain.util.GlobalUtil;

/*
 * 用户服务
 */
@Service
public class UserService {

	private static int smsCodeSourceLen = GlobalConst.smsCodeSource.length();
	private static int inviteCodeSourceLen = GlobalConst.inviteCodeSource.length();

	@Autowired
	private UserRepository userRepository;

	// 生成验证码
	public String produceSmsCode() {
		String smsCode = "";
		for (int i = 0; i < GlobalConst.smsCodeLen; i++) {
			smsCode += GlobalConst.smsCodeSource.charAt(GlobalUtil.random.nextInt(smsCodeSourceLen));
		}
//		return smsCode;
		return "123456";
	}

	// 生成邀请码
	public String produceInviteCode() {
		String inviteCode = "";
		do {
			for (int i = 0; i < GlobalConst.inviteCodeLen; i++) {
				inviteCode += GlobalConst.inviteCodeSource.charAt(GlobalUtil.random.nextInt(inviteCodeSourceLen));
			}
			if (userRepository.findByInviteCode(inviteCode) != null) {
				inviteCode = "";
			}
		} while (StringUtils.isEmpty(inviteCode));
		return inviteCode;
	}
}
