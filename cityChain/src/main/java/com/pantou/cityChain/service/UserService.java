package com.pantou.cityChain.service;

import org.springframework.stereotype.Service;

import com.pantou.cityChain.consts.LangConst;
import com.pantou.cityChain.entity.UserEntity;
import com.pantou.cityChain.vo.JsonBase;

@Service
public class UserService {
	
	public void doLogin(JsonBase jsonBase, UserEntity userEntity) {
		jsonBase.init(LangConst.baseSuccess);
		jsonBase.setObject(userEntity);
	}
}
