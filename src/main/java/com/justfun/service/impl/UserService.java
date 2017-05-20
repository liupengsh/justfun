package com.justfun.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.justfun.dao.UserDao;
import com.justfun.model.User;
import com.justfun.service.IUserService;

@Service("userService")
public class UserService implements IUserService {

	private Logger logger = Logger.getLogger(UserService.class);
	
	@Resource  
    private UserDao userDao;
	
	@Override
	public User findUserById(Long userId) {
		return userDao.findUserById(userId);
	}

	@Override
	public User findUserByUserName(String userName) {
		logger.info("findUserByUserName:" + userName);
		return userDao.findUserByUserName(userName);
	}

}
