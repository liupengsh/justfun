package com.justfun;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.justfun.model.User;
import com.justfun.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})  

public class TestUserService {
	
	private static Logger logger = Logger.getLogger(TestUserService.class); 
	
	@Resource  
    private IUserService userService;
	
	@Test
	public void test1() {
	    User user = userService.findUserById(1l);
	    logger.info(JSON.toJSONString(user));
	}
}
