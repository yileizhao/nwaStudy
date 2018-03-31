package com.pantou.cityChain.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.pantou.cityChain.consts.CoinEnum;
import com.pantou.cityChain.consts.GlobalConst;
import com.pantou.cityChain.entity.CoinDayEntity;
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
	private UserService userService;

	@Autowired
	private CoinDayRepository coinDayRepository;

	/*
	 * 自动发放city币 第一次延迟1秒执行，当执行完后2秒再执行：@Scheduled(initialDelay = 1000, fixedDelay =
	 * 2000)
	 */
	@Scheduled(cron = "0 * * * * ?")
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
	@Scheduled(cron = "0 4 * * * ?")
	public void dayCoinTotal() {
		Map<CoinEnum, Double> calAllCoinsSum = userService.calAllCoinsSum();
		CoinDayEntity coinDayEntity = coinDayRepository.findByDate(TimeUtil.sdfYmd.format(new Date(TimeUtil.now())));
		JSONObject historyTotal = new JSONObject();
		JSONObject dayTotal = new JSONObject();
		for (CoinEnum coinEnum : CoinEnum.values()) {
			String coinEnumName = coinEnum.name();
			historyTotal.put(coinEnumName, calAllCoinsSum.get(coinEnum));
			dayTotal.put(coinEnumName, calAllCoinsSum.get(coinEnum)
					- (coinDayEntity == null ? 0 : coinDayEntity.getHistoryTotal().getDoubleValue(coinEnumName)));
		}
		CoinDayEntity coinDayEntityResult = new CoinDayEntity(TimeUtil.sdfYmd.format(new Date(TimeUtil.now())),
				historyTotal, dayTotal);
		coinDayRepository.save(coinDayEntityResult);
		System.out.println("city币总量每日统计: " + JSONObject.toJSONString(coinDayEntityResult));
	}
}
