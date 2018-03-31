package com.pantou.cityChain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.alibaba.fastjson.JSONObject;

/*
 * city币总量每日统计实体
 */
@Entity
public class CoinDayEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String date; // 日期
	private JSONObject historyTotal; // city币总量
	private JSONObject dayTotal; // city币每日产量

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public JSONObject getHistoryTotal() {
		return historyTotal;
	}

	public void setHistoryTotal(JSONObject historyTotal) {
		this.historyTotal = historyTotal;
	}

	public JSONObject getDayTotal() {
		return dayTotal;
	}

	public void setDayTotal(JSONObject dayTotal) {
		this.dayTotal = dayTotal;
	}

}