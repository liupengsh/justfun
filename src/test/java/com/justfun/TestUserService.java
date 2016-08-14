package com.justfun;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.justfun.model.User;
import com.justfun.model.dto.Menu;
import com.justfun.service.IResourceService;
import com.justfun.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})  

public class TestUserService {
	
	private static Logger logger = Logger.getLogger(TestUserService.class); 
	
	@Resource  
    private IUserService userService;
	
	@Resource
	private IResourceService resourceService;
	
	@Test
	public void testFindUserById() {
	    User user = userService.findUserById(1l);
	    logger.info(JSON.toJSONString(user));
	}
	
	@Test
	public void testFindUserByUserName() {
		User user = userService.findUserByUserName("admin");
		logger.info(JSON.toJSONString(user));
		List<Menu> lstMenu = resourceService.findMenus(user);
		logger.info(JSON.toJSONString(lstMenu));
	}
}
