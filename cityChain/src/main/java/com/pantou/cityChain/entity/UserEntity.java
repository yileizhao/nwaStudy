package com.pantou.cityChain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String mobile; // 手机号
	private String smsCode; // 短信验证码
	private long smsCodeTime; // 短信验证码，最后发送时间
	private String inviteCode; // 邀请码
	private int inviteCodeCnt; // 邀请码，已邀请的次数
	private String nickname; // 昵称
	private String name; // 姓名
	private String idcard; // 身份证号码
	private long idcardTime; // 身份证号码，最后校验时间

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

	public long getSmsCodeTime() {
		return smsCodeTime;
	}

	public void setSmsCodeTime(long smsCodeTime) {
		this.smsCodeTime = smsCodeTime;
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

	public long getIdcardTime() {
		return idcardTime;
	}

	public void setIdcardTime(long idcardTime) {
		this.idcardTime = idcardTime;
	}

}
