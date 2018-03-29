package com.pantou.cityChain.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * 更改Freemarker模板引擎默认设置
 */
@Configuration
public class FreemarkerConfig {

	@Autowired
	protected freemarker.template.Configuration configuration;

	@PostConstruct  
    public void  setSharedVariable(){  
		configuration.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
	}
}
