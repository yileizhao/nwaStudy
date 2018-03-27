package com.pantou.cityChain.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.pantou.cityChain.consts.CoinEnum;
import com.pantou.cityChain.consts.GlobalConst;
import com.pantou.cityChain.consts.LangConst;
import com.pantou.cityChain.entity.UserEntity;
import com.pantou.cityChain.repository.RedisRepository;
import com.pantou.cityChain.repository.UserRepository;
import com.pantou.cityChain.util.TimeUtil;
import com.pantou.cityChain.vo.JsonBase;
import com.pantou.cityChain.vo.ThreeTuple;

/*
 * 基地控制器
 */
@RestController
public class BaseController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RedisRepository redisRepository;

	/*
	 * 主页
	 */
	@RequestMapping("/base/main")
	public JsonBase main(@RequestParam String token) {
		JsonBase jsonBase = new JsonBase();
		if (!StringUtils.isEmpty(token)) {
			UserEntity userEntity = userRepository.findByToken(token);
			if (userEntity == null || StringUtils.isEmpty(userEntity.getName())) { // 基地未实名认证
				jsonBase.init(LangConst.baseMainNotCertification);
			} else { // 有效请求
				long now = TimeUtil.now();
				if (!TimeUtil.isSameDay(userEntity.getTimeBase(), now)) { // 每日登录，增加原力
					userEntity.setTimeBase(now);
					userRepository.save(userEntity);
				}
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("coins", redisRepository.getMapAll(GlobalConst.redisMapCoinHavest + userEntity.getId()));
				jsonObject.put("nextRefTime",
						60 - Integer.parseInt(TimeUtil.sdfYmdhms.format(new Date(now)).substring(17)));
				jsonBase.init(LangConst.baseSuccess);
				jsonBase.setObject(jsonObject);
			}
		} else { // 参数错误
			jsonBase.init(LangConst.baseParamError);
		}

		return jsonBase;
	}

	/*
	 * 收获
	 */
	@RequestMapping("/base/havest")
	public JsonBase havest(@RequestParam String token, @RequestParam String field) {
		JsonBase jsonBase = new JsonBase();
		if (!StringUtils.isEmpty(token) && !StringUtils.isEmpty(field)) {
			UserEntity userEntity = userRepository.findByToken(token);
			if (userEntity == null || StringUtils.isEmpty(userEntity.getName())) { // 收获未实名认证
				jsonBase.init(LangConst.baseMainNotCertification);
			} else { // 有效请求
				long id = userEntity.getId();
				String key = GlobalConst.redisMapCoinHavest + id;
				Map<String, Object> map = redisRepository.getMapAll(key);
				if (!map.containsKey(field)) { // 收获不存在
					jsonBase.init(LangConst.baseHavestNotExisit);
				} else { // 收获
					// TODO redis收获记录过量，存到mysql
					double harvest = (double) map.get(field);
					userEntity.setCoinCity(userEntity.getCoinCity() + harvest);
					userRepository.save(userEntity);
					redisRepository.delMapField(key, field);
					redisRepository.addList(GlobalConst.redisListCoinHistory + id,
							new ThreeTuple<Long, CoinEnum, Double>(TimeUtil.now(), CoinEnum.CoinCity, harvest));
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("field", field);
					jsonBase.init(LangConst.baseSuccess);
					jsonBase.setObject(jsonObject);
				}
			}
		} else { // 参数错误
			jsonBase.init(LangConst.baseParamError);
		}

		return jsonBase;
	}

	/*
	 * 币收获记录
	 */
	@RequestMapping("/base/history")
	public JsonBase history(@RequestParam String token) {
		JsonBase jsonBase = new JsonBase();
		if (!StringUtils.isEmpty(token)) {
			UserEntity userEntity = userRepository.findByToken(token);
			if (userEntity == null || StringUtils.isEmpty(userEntity.getName())) { // 收获未实名认证
				jsonBase.init(LangConst.baseMainNotCertification);
			} else { // 有效请求
				jsonBase.init(LangConst.baseSuccess);
				jsonBase.setObject(
						redisRepository.getList(GlobalConst.redisListCoinHistory + userEntity.getId(), 0, 64));
			}
		} else { // 参数错误
			jsonBase.init(LangConst.baseParamError);
		}

		return jsonBase;
	}
}
