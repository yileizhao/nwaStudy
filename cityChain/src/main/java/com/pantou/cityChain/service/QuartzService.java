package com.pantou.cityChain.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pantou.cityChain.repository.UserRepository;

@Component
public class QuartzService {

	@Autowired
	private UserRepository userRepository;
	
	@Scheduled(cron = "0 * * * * *")
	public void timer() {
//		GlobalConst.coinCityTotalPerHour;
		System.out.println(userRepository.queryActiveUser(1).size());
		
		// 获取当前时间
		LocalDateTime localDateTime = LocalDateTime.now();
		System.out.println("当前时间为:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	}
}
