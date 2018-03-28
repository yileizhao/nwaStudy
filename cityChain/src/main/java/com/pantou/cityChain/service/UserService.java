package com.pantou.cityChain.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pantou.cityChain.consts.GlobalConst;
import com.pantou.cityChain.repository.UserRepository;
import com.pantou.cityChain.util.GlobalUtil;
import com.pantou.cityChain.vo.TwoTuple;

/*
 * 用户服务
 */
@Service
@SuppressWarnings("unchecked")
public class UserService {

	private static int smsCodeSourceLen = GlobalConst.smsCodeSource.length();
	private static int inviteCodeSourceLen = GlobalConst.inviteCodeSource.length();

	@Autowired
	private UserRepository userRepository;

	@PersistenceContext
	private EntityManager entityManager;

	// 生成验证码
	public String produceSmsCode() {
		String smsCode = "";
		for (int i = 0; i < GlobalConst.smsCodeLen; i++) {
			smsCode += GlobalConst.smsCodeSource.charAt(GlobalUtil.random.nextInt(smsCodeSourceLen));
		}
		// return smsCode;
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

	/*
	 * 动态拼接查询：page-分页支持
	 */
	public <T> List<T> findBysql(T t, String sql, TwoTuple<Integer, Integer> page) {
		Query query = entityManager.createQuery(sql);
		if (page != null) {
			query.setFirstResult(page.first * page.second);
			query.setMaxResults(page.second);
		}
		List<T> list = query.getResultList();
		entityManager.close();
		return list;
	}
}
