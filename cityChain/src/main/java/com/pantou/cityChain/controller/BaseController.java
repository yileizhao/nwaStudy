package com.pantou.cityChain.controller;

import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.pantou.cityChain.consts.CoinEnum;
import com.pantou.cityChain.consts.GlobalConst;
import com.pantou.cityChain.consts.LangConst;
import com.pantou.cityChain.consts.PlusMinusEnum;
import com.pantou.cityChain.consts.PowerEnum;
import com.pantou.cityChain.consts.TypeEnum;
import com.pantou.cityChain.entity.HistoryEntity;
import com.pantou.cityChain.entity.PowerHistoryEntity;
import com.pantou.cityChain.entity.UserEntity;
import com.pantou.cityChain.repository.HistoryRepository;
import com.pantou.cityChain.repository.PowerHistoryRepository;
import com.pantou.cityChain.repository.RedisRepository;
import com.pantou.cityChain.repository.UserRepository;
import com.pantou.cityChain.util.TimeUtil;
import com.pantou.cityChain.vo.JsonBase;

/*
 * 基地控制器
 */
@RestController
public class BaseController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RedisRepository redisRepository;

	@Autowired
	private HistoryRepository historyRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private PowerHistoryRepository powerHistoryRepository;

	/*
	 * 主页
	 */
	@RequestMapping("/base/main")
	public JsonBase main(@RequestParam String token) {
		JsonBase jsonBase = new JsonBase();
		if (!StringUtils.isEmpty(token)) {
			UserEntity userEntity = userRepository.findByToken(token);
			if (userEntity == null) { // 令牌错误
				jsonBase.init(LangConst.baseToken);
			} else { // 有效请求
				long now = TimeUtil.now();
				if (!TimeUtil.isSameDay(userEntity.getTimeBase(), now)) { // 每日登录，增加原力
					userEntity.setTimeBase(now);
					userEntity.setPower(userEntity.getPower() + GlobalConst.loginPower);
					userRepository.save(userEntity);
					powerHistoryRepository.save(new PowerHistoryEntity(userEntity.getId(), now, GlobalConst.loginPower,
							PowerEnum.DayLogin));
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
	public JsonBase havest(@RequestParam String token, @RequestParam String coinKey) {
		JsonBase jsonBase = new JsonBase();
		if (!StringUtils.isEmpty(token) && !StringUtils.isEmpty(coinKey)) {
			UserEntity userEntity = userRepository.findByToken(token);
			if (userEntity == null) { // 令牌错误
				jsonBase.init(LangConst.baseToken);
			} else { // 有效请求
				long id = userEntity.getId();
				String key = GlobalConst.redisMapCoinHavest + id;
				Map<String, Object> map = redisRepository.getMapAll(key);
				if (!map.containsKey(coinKey)) { // 收获不存在
					jsonBase.init(LangConst.baseHavestNotExisit);
				} else { // 收获
					// TODO redis收获记录过量，存到mysql
					double harvest = (double) map.get(coinKey);
					userEntity.setCoinCity(userEntity.getCoinCity() + harvest);
					userRepository.save(userEntity);
					redisRepository.delMapField(key, coinKey);
					historyRepository.save(new HistoryEntity(id, TimeUtil.now(), harvest, CoinEnum.CoinCity,
							TypeEnum.Reward, PlusMinusEnum.Plus));

					JSONObject jsonObject = new JSONObject();
					jsonObject.put("coinKey", coinKey);
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
	 * 收获记录
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/base/history")
	public JsonBase history(@RequestParam String token, @RequestParam int coin, @RequestParam int type,
			@RequestParam int plusMinus, @RequestParam int page) {
		JsonBase jsonBase = new JsonBase();
		if (!StringUtils.isEmpty(token)) {
			UserEntity userEntity = userRepository.findByToken(token);
			if (userEntity == null) { // 令牌错误
				jsonBase.init(LangConst.baseToken);
			} else { // 有效请求
				jsonBase.init(LangConst.baseSuccess);
				jsonBase.setObject(historyRepository.getByCoinAndTypeAndPlusMinus(CoinEnum.values()[coin],
						TypeEnum.values()[type], PlusMinusEnum.values()[plusMinus],
						new PageRequest(page < 0 ? 0 : page, GlobalConst.coinHisotoryPageSize)));
			}
		} else { // 参数错误
			jsonBase.init(LangConst.baseParamError);
		}

		return jsonBase;
	}
}
