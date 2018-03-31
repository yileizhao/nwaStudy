package com.pantou.cityChain.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

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

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "historyTotal", columnDefinition = "BLOB", nullable = true)
	private JSONObject historyTotal; // city币总量

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "dayTotal", columnDefinition = "BLOB", nullable = true)
	private JSONObject dayTotal; // city币每日产量

	public CoinDayEntity() {
		super();
	}

	public CoinDayEntity(String date, JSONObject historyTotal, JSONObject dayTotal) {
		super();
		this.date = date;
		this.historyTotal = historyTotal;
		this.dayTotal = dayTotal;
	}

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
