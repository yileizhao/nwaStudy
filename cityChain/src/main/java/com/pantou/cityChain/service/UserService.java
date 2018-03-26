package com.pantou.cityChain.service;

import org.springframework.stereotype.Service;

import com.pantou.cityChain.entity.UserEntity;
import com.pantou.cityChain.util.I18nUtil;
import com.pantou.cityChain.vo.JsonBase;

@Service
public class UserService {
	
	public void doLogin(JsonBase jsonBase, UserEntity userEntity) {
		jsonBase.init(I18nUtil.baseSuccess);
		jsonBase.setObject(userEntity);
	}
}
