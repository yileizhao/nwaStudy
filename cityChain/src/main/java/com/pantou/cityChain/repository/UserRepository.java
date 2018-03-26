package com.pantou.cityChain.repository;

import org.springframework.data.repository.CrudRepository;

import com.pantou.cityChain.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

	// 根据手机号查询，方法名要以findBy+字段名来命名
	public UserEntity findByMobile(String mobile);
	
	// 根据邀请码查询
	public UserEntity findByInviteCode(String inviteCode);
}
