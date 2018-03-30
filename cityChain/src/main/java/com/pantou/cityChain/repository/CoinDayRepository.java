package com.pantou.cityChain.repository;

import org.springframework.data.repository.CrudRepository;

import com.pantou.cityChain.entity.CoinDayEntity;

/**
 * city币总量每日统计仓库
 */
public interface CoinDayRepository extends CrudRepository<CoinDayEntity, Long> {

	/*
	 * 根据日期查询
	 */
	public CoinDayEntity findByDate(String date);
}
