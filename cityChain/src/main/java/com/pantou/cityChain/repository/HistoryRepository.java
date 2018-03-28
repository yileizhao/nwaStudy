package com.pantou.cityChain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pantou.cityChain.consts.CoinEnum;
import com.pantou.cityChain.consts.PlusMinusEnum;
import com.pantou.cityChain.consts.TypeEnum;
import com.pantou.cityChain.entity.HistoryEntity;

/**
 * 币历史记录仓库
 */
public interface HistoryRepository extends CrudRepository<HistoryEntity, Long> {

	// 根据条件获取记录
	@Query("select he from HistoryEntity he where he.coin = ?1 and he.type = ?2 and he.plusMinus = ?3")
	public Page<HistoryEntity> getByCoinAndTypeAndPlusMinus(CoinEnum coinEnum, TypeEnum typeEnum,
			PlusMinusEnum plusMinusEnum, Pageable pageable);

}
