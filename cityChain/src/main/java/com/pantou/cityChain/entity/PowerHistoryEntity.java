package com.pantou.cityChain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.pantou.cityChain.consts.PowerEnum;

/*
 * 原力历史记录实体
 */
@Entity
public class PowerHistoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private long userId; // 用户id
	private long time; // 创建时间
	private int cnt; // 数量
	private PowerEnum power;

	public PowerHistoryEntity() {
		super();
	}

	public PowerHistoryEntity(long userId, long time, int cnt, PowerEnum power) {
		super();
		this.userId = userId;
		this.time = time;
		this.cnt = cnt;
		this.power = power;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public PowerEnum getPower() {
		return power;
	}

	public void setPower(PowerEnum power) {
		this.power = power;
	}

}
