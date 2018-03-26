package com.pantou.cityChain.repository;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepository {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	private static final String KEY_PREFIX_VALUE = "citiChain:value:";

	public boolean cacheValue(String k, String v, long time) {
		String key = KEY_PREFIX_VALUE + k;
		try {
			ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
			valueOps.set(key, v);
			if (time > 0) {
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			}
			return true;
		} catch (Throwable t) {
			LOGGER.error("缓存[" + key + "]失败, value[" + v + "]", t);
		}
		return false;
	}
	
	public String getValue(String k) {
		try {
			ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
			return valueOps.get(KEY_PREFIX_VALUE + k);
		} catch (Throwable t) {
			LOGGER.error("获取缓存失败key[" + KEY_PREFIX_VALUE + k + ", Codeor[" + t + "]");
		}
		return null;
	}
}
