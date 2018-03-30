package com.pantou.cityChain.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pantou.cityChain.consts.CoinEnum;
import com.pantou.cityChain.consts.GlobalConst;
import com.pantou.cityChain.repository.CoinDayRepository;
import com.pantou.cityChain.repository.RedisRepository;
import com.pantou.cityChain.repository.UserRepository;
import com.pantou.cityChain.util.TimeUtil;

/*
 * 定时器服务
 */
@Component
public class QuartzService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RedisRepository redisRepository;
	
	@Autowired
	private CoinDayRepository coinDayRepository;

	/*
	 * 自动发放city币
	 */
	@Scheduled(cron = "0 * * * * *")
	public void autoCityHarvest() {
		// TODO 宕机补差
		long now = TimeUtil.now();
		long time = now - GlobalConst.baseCityCoinAddTime;
		Long powerTotalObject = userRepository.queryActiveUserPowerTotal(time);
		// TODO 上线前改为0
		double powerTotal = (powerTotalObject == null ? 0 : powerTotalObject);
		List<Object> objects = userRepository.queryActiveUsers(time);
		double powerAvg = powerTotal / objects.size();
		double totalAdd = 0;
		if (powerTotal > 0 && !objects.isEmpty()) {
			for (Object object : objects) {
				Object[] objectArr = (Object[]) object;
				String key = GlobalConst.redisMapCoinHarvest + objectArr[0];
				if (redisRepository.getMapAll(key).size() < GlobalConst.baseCityCoinMax) {
					double add = (int) objectArr[1] / powerAvg * GlobalConst.coinCityPerHour;
					totalAdd += add;
					redisRepository.addMapField(key, now + "", add);
					WebSocketService.sendMessageAll((String) objectArr[2] + "获得" + add + CoinEnum.CoinCity.getValue());
				}
			}
		}
		System.out.println("发放city币: " + totalAdd + "@" + TimeUtil.sdfYmdhms.format(new Date(now)));
	}
	
	/*
	 * city币总量每日统计
	 */
	@Scheduled(cron = "0 0 * * * *")
	public void dayCoinTotal() {
		System.out.println("发放city币: " + 1 + "@" + TimeUtil.sdfYmdhms.format(new Date(1)));
	}
}
