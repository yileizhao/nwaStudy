package com.pantou.cityChain.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pantou.cityChain.consts.GlobalConst;
import com.pantou.cityChain.repository.RedisRepository;
import com.pantou.cityChain.repository.UserRepository;
import com.pantou.cityChain.util.TimeUtil;

@Component
public class QuartzService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RedisRepository redisRepository;

	/*
	 * 自动发放city币
	 */
	@Scheduled(cron = "0 * * * * *")
	public void timer() {
		// TODO 宕机补差
		long now = TimeUtil.now();
		long time = now - GlobalConst.baseCityCoinAddTime;
		long powerTotal = userRepository.queryActiveUserPowerTotal(time);
		List<Object> objects = userRepository.queryActiveUsers(time);
		double totalAdd = 0;
		if (powerTotal > 0) {
			for (Object object : objects) {
				Object[] objectArr = (Object[]) object;
				String key = GlobalConst.redisMapCoinHavest + objectArr[0];
				if (redisRepository.getMapAll(key).size() < GlobalConst.baseCityCoinMax) {
					double add = (int) objectArr[1] / (double) powerTotal * GlobalConst.coinCityTotalPerHour;
					totalAdd += add;
					redisRepository.addMapField(key, now + "", add);
				}
			}
		}
		System.out.println("发放city币: " + totalAdd + "@" + TimeUtil.sdfYmdhms.format(new Date(now)));
	}
}
