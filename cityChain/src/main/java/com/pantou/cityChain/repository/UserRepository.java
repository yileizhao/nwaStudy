package com.pantou.cityChain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pantou.cityChain.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

	// 根据手机号查询，方法名要以findBy+字段名来命名
	public UserEntity findByMobile(String mobile);
	
	// 根据邀请码查询
	public UserEntity findByInviteCode(String inviteCode);
	
	// 根据令牌查询
	public UserEntity findByToken(String token);
	
	// 获取活跃用户
	@Query("select ue from UserEntity ue where ue.timeBase > ?1")
    public List<UserEntity> queryActiveUser(long time);
	
	/*
	 *  @Modifying
		@Query("update Person set email = :email where lastName =:lastName")
		void updatePersonEmailByLastName(@Param("lastName")String lastName,@Param("email")String email);
	 */
}
