package com.pantou.cityChain.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.pantou.cityChain.consts.CoinEnum;
import com.pantou.cityChain.consts.GlobalConst;
import com.pantou.cityChain.consts.LangConst;
import com.pantou.cityChain.entity.HistoryEntity;
import com.pantou.cityChain.entity.PowerHistoryEntity;
import com.pantou.cityChain.entity.UserEntity;
import com.pantou.cityChain.repository.RedisRepository;
import com.pantou.cityChain.repository.UserRepository;
import com.pantou.cityChain.service.UserService;
import com.pantou.cityChain.util.TimeUtil;
import com.pantou.cityChain.vo.FourTuple;
import com.pantou.cityChain.vo.JsonBase;
import com.pantou.cityChain.vo.TwoTuple;

/**
 * html5管理
 */
@Controller
public class H5Controller {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private RedisRepository redisRepository;

	/**
	 * 介绍
	 */
	@RequestMapping("/h5/intro")
	public String intro(Map<String, Object> map) {
		map.put("total", 10000);
		map.put("yesterday", 100);
		return "intro";
	}

	/**
	 * 攻略
	 */
	@RequestMapping("/h5/strategy")
	public String strategy(Map<String, Object> map) {
		return "strategy";
	}

	/**
	 * 任务
	 */
	@RequestMapping("/h5/task")
	public String task(Map<String, Object> map) {
		return "task";
	}

	/**
	 * 基地
	 */
	@RequestMapping("/h5/base")
	public String base(@RequestParam String token, Map<String, Object> map) {
		JsonBase jsonBase = new JsonBase();
		if (!StringUtils.isEmpty(token)) {
			UserEntity userEntity = userRepository.findByToken(token);
			if (userEntity == null) { // 令牌错误
				jsonBase.init(LangConst.baseToken);
			} else { // 有效请求
				jsonBase.init(LangConst.baseSuccess);

				map.put("token", token);
				map.put("power", userEntity.getPower());
				List<FourTuple<String, Double, Float, Float>> coins = new ArrayList<FourTuple<String, Double, Float, Float>>();
				String sql = "select he from HistoryEntity he";
				map.put("history", userService.findBysql(HistoryEntity.class, sql,
						new TwoTuple<Integer, Integer>(0, GlobalConst.coinHisotoryPageSize)));
				int index = 0;
				for (Entry<String, Object> entry : redisRepository
						.getMapAll(GlobalConst.redisMapCoinHarvest + userEntity.getId()).entrySet()) {
					TwoTuple<Float, Float> twoTuple = GlobalConst.popXys.get(index++);
					coins.add(new FourTuple<String, Double, Float, Float>(entry.getKey(), (Double) entry.getValue(),
							twoTuple.first, twoTuple.second));
				}
				map.put("coins", coins);
				map.put("nextRefTime",
						60 - Integer.parseInt(TimeUtil.sdfYmdhms.format(new Date(TimeUtil.now())).substring(17)));
			}
		} else { // 参数错误
			jsonBase.init(LangConst.baseParamError);
		}

		if (jsonBase.getCode() != LangConst.baseSuccess.first) {
			map.put("jsonBase", JSONObject.toJSONString(jsonBase));
			return "error";
		} else {
			return "base";
		}
	}

	/**
	 * 资产
	 */
	@RequestMapping("/h5/asset")
	public String asset(@RequestParam String token, Map<String, Object> map) {
		JsonBase jsonBase = new JsonBase();
		if (!StringUtils.isEmpty(token)) {
			UserEntity userEntity = userRepository.findByToken(token);
			if (userEntity == null) { // 令牌错误
				jsonBase.init(LangConst.baseToken);
			} else { // 有效请求
				jsonBase.init(LangConst.baseSuccess);

				List<TwoTuple<CoinEnum, Double>> coins = new ArrayList<TwoTuple<CoinEnum, Double>>();
				coins.add(new TwoTuple<CoinEnum, Double>(CoinEnum.CoinCity, userEntity.getCoinCity()));
				coins.add(new TwoTuple<CoinEnum, Double>(CoinEnum.CoinAtm, userEntity.getCoinAtm()));
				map.put("coins", coins);
			}
		} else { // 参数错误
			jsonBase.init(LangConst.baseParamError);
		}

		if (jsonBase.getCode() != LangConst.baseSuccess.first) {
			map.put("jsonBase", JSONObject.toJSONString(jsonBase));
			return "error";
		} else {
			return "asset";
		}
	}

	/**
	 * 币种介绍
	 */
	@RequestMapping("/h5/assetIntro")
	public String assetIntro(@RequestParam String coin, @RequestParam String cnt, Map<String, Object> map) {
		JsonBase jsonBase = new JsonBase();
		if (!StringUtils.isEmpty(coin) && !StringUtils.isEmpty(coin)) {
			jsonBase.init(LangConst.baseSuccess);

			map.put("coin", coin);
			map.put("cnt", cnt);
		} else { // 参数错误
			jsonBase.init(LangConst.baseParamError);
		}

		if (jsonBase.getCode() != LangConst.baseSuccess.first) {
			map.put("jsonBase", JSONObject.toJSONString(jsonBase));
			return "error";
		} else {
			return "assetIntro";
		}
	}

	/**
	 * 原力记录
	 */
	@RequestMapping("/h5/power")
	public String power(@RequestParam String token, Map<String, Object> map) {
		JsonBase jsonBase = new JsonBase();
		if (!StringUtils.isEmpty(token)) {
			UserEntity userEntity = userRepository.findByToken(token);
			if (userEntity == null) { // 令牌错误
				jsonBase.init(LangConst.baseToken);
			} else { // 有效请求
				jsonBase.init(LangConst.baseSuccess);

				map.put("power", userEntity.getPower());
				String sql = "select phe from PowerHistoryEntity phe where phe.userId = " + userEntity.getId();
				map.put("powerHistory", userService.findBysql(PowerHistoryEntity.class, sql,
						new TwoTuple<Integer, Integer>(0, GlobalConst.coinHisotoryPageSize)));
			}
		} else { // 参数错误
			jsonBase.init(LangConst.baseParamError);
		}

		if (jsonBase.getCode() != LangConst.baseSuccess.first) {
			map.put("jsonBase", JSONObject.toJSONString(jsonBase));
			return "error";
		} else {
			return "power";
		}
	}

	/**
	 * 资产记录
	 */
	@RequestMapping("/h5/history")
	public String history(@RequestParam String token, Map<String, Object> map) {
		JsonBase jsonBase = new JsonBase();
		if (!StringUtils.isEmpty(token)) {
			UserEntity userEntity = userRepository.findByToken(token);
			if (userEntity == null) { // 令牌错误
				jsonBase.init(LangConst.baseToken);
			} else { // 有效请求
				jsonBase.init(LangConst.baseSuccess);

				String sql = "select he from HistoryEntity he";
				map.put("history", userService.findBysql(HistoryEntity.class, sql,
						new TwoTuple<Integer, Integer>(0, GlobalConst.coinHisotoryPageSize)));
			}
		} else { // 参数错误
			jsonBase.init(LangConst.baseParamError);
		}

		if (jsonBase.getCode() != LangConst.baseSuccess.first) {
			map.put("jsonBase", JSONObject.toJSONString(jsonBase));
			return "error";
		} else {
			return "history";
		}
	}
}