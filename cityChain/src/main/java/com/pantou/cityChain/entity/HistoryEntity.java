package com.pantou.cityChain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.pantou.cityChain.consts.CoinEnum;
import com.pantou.cityChain.consts.PlusMinusEnum;
import com.pantou.cityChain.consts.TypeEnum;

@Entity
public class HistoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private long time; // 创建时间
	private double cnt; // 数量：正数-收获 负数-消耗
	private CoinEnum coin;
	private TypeEnum type;
	private PlusMinusEnum plusMinus;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public double getCnt() {
		return cnt;
	}

	public void setCnt(double cnt) {
		this.cnt = cnt;
	}

	public CoinEnum getCoin() {
		return coin;
	}

	public void setCoin(CoinEnum coin) {
		this.coin = coin;
	}

	public TypeEnum getType() {
		return type;
	}

	public void setType(TypeEnum type) {
		this.type = type;
	}

	public PlusMinusEnum getPlusMinus() {
		return plusMinus;
	}

	public void setPlusMinus(PlusMinusEnum plusMinus) {
		this.plusMinus = plusMinus;
	}

}