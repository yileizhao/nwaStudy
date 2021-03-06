package com.pantou.cityChain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pantou.cityChain.entity.UserEntity;

/**
 * 用户仓库
 */
public interface UserRepository extends CrudRepository<UserEntity, Long> {

	/*
	 * 根据手机号查询，方法名要以findBy+字段名来命名
	 */
	public UserEntity findByMobile(String mobile);

	/*
	 * 根据昵称查询
	 */
	public UserEntity findByNickname(String nickname);

	/*
	 * 根据邀请码查询
	 */
	public UserEntity findByInviteCode(String inviteCode);

	/*
	 * 根据令牌查询
	 */
	public UserEntity findByToken(String token);

	/*
	 * 获取活跃用户总原力，无用户时返回null
	 */
	@Query("select sum(power) from UserEntity ue where ue.timeBase > ?1")
	public Long queryActiveUserPowerTotal(long time);

	/*
	 * 获取活跃用户
	 */
	@Query("select ue.id, ue.power, ue.nickname from UserEntity ue where ue.timeBase > ?1")
	public List<Object> queryActiveUsers(long time);

	/*
	 * 获取所有用户的币
	 */
	@Query("select ue.coins from UserEntity ue")
	public List<Object> queryAllCoins();

}
