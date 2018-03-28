package com.pantou.cityChain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pantou.cityChain.entity.HistoryEntity;

public interface HistoryRepository extends CrudRepository<HistoryEntity, Long> {

	// 根据条件获取记录
	@Query("select he from HistoryEntity he where he.coin = ?1 and he.type = ?2 and he.plusMinus = ?3 limit start, coinHisotoryPageSize")
	public List<HistoryEntity> queryByChoice(int coin, int type, int plusMinus, int start, int coinHisotoryPageSize);

}
