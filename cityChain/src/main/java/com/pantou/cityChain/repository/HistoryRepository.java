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

	/*
	 * 根据条件获取记录: userEntity.getId(), CoinEnum.values()[coin],
	 * TypeEnum.values()[type], PlusMinusEnum.values()[plusMinus], new
	 * PageRequest(page < 0 ? 0 : page, GlobalConst.coinHisotoryPageSize)
	 */
	@Query("select he from HistoryEntity he where he.userId = ?1 and he.coin = ?2 and he.type = ?3 and he.plusMinus = ?4")
	public Page<HistoryEntity> getByCoinAndTypeAndPlusMinus(long id, CoinEnum coinEnum, TypeEnum typeEnum,
			PlusMinusEnum plusMinusEnum, Pageable pageable);

}
