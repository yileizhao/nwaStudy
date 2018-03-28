package com.pantou.cityChain.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * redis管理仓库
 */
@Repository
public class RedisRepository {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public void addMapField(String key, String field, Object value) {
		redisTemplate.opsForHash().put(key, field, value);
	}

	public Map<String, Object> getMapAll(String key) {
		Map<String, Object> result = new HashMap<String, Object>();
		for (Entry<Object, Object> entry : redisTemplate.opsForHash().entries(key).entrySet()) {
			result.put((String) entry.getKey(), entry.getValue());
		}
		return result;
	}

	public void delMapField(String key, String field) {
		redisTemplate.opsForHash().delete(key, field);
	}

	public void addList(String key, Object value) {
		redisTemplate.opsForList().leftPush(key, value);
	}
	
	public List<Object> getList(String key, long start, long end) {
		return redisTemplate.opsForList().range(key, start, end);
	}
}
