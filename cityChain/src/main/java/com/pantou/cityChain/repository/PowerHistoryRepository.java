package com.pantou.cityChain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pantou.cityChain.consts.PowerEnum;
import com.pantou.cityChain.entity.HistoryEntity;
import com.pantou.cityChain.entity.PowerHistoryEntity;

/**
 * 原力历史记录仓库
 */
public interface PowerHistoryRepository extends CrudRepository<PowerHistoryEntity, Long> {

	// 根据条件获取记录
	@Query("select phe from PowerHistoryEntity phe where phe.power = ?1")
	public Page<HistoryEntity> getByCoinAndTypeAndPlusMinus(PowerEnum power, Pageable pageable);

}
