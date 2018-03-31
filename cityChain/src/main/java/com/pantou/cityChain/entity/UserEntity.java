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
import com.pantou.cityChain.consts.CoinEnum;

/*
 * 用户历史记录实体
 */
@Entity
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String mobile; // 手机号
	private String smsCode; // 短信验证码
	private long timeSmsCode; // 短信验证码，最后发送时间
	private String inviteCode; // 邀请码
	private int inviteCodeCnt; // 邀请码，已邀请的次数
	private String nickname; // 昵称
	private int power; // 原力值
	private String name; // 姓名
	private String idcard; // 身份证号码
	private long timeIdcard; // 身份证号码，最后校验时间

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(length = 65535)
	private JSONObject coins = new JSONObject(); // 各种币数量

	private String token; // 令牌
	private long timeBase; // 最后访问基地时间（日活跃）
	private byte[] qrCode; // 二维码数组

	// 币增减
	public void addCoin(CoinEnum coincity, double cnt) {
		String coinName = coincity.name();
		if (CoinEnum.valueOf(coinName) != null) {
			coins.put(coinName, (coins.containsKey(coinName) ? coins.getDouble((coinName)) : 0) + cnt);
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public long getTimeSmsCode() {
		return timeSmsCode;
	}

	public void setTimeSmsCode(long timeSmsCode) {
		this.timeSmsCode = timeSmsCode;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public int getInviteCodeCnt() {
		return inviteCodeCnt;
	}

	public void setInviteCodeCnt(int inviteCodeCnt) {
		this.inviteCodeCnt = inviteCodeCnt;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public long getTimeIdcard() {
		return timeIdcard;
	}

	public void setTimeIdcard(long timeIdcard) {
		this.timeIdcard = timeIdcard;
	}

	public JSONObject getCoins() {
		return coins;
	}

	public void setCoins(JSONObject coins) {
		this.coins = coins;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getTimeBase() {
		return timeBase;
	}

	public void setTimeBase(long timeBase) {
		this.timeBase = timeBase;
	}

	public byte[] getQrCode() {
		return qrCode;
	}

	public void setQrCode(byte[] qrCode) {
		this.qrCode = qrCode;
	}

}
