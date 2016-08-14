package com.justfun.service;

import com.justfun.model.User;

public interface IUserService {

	public User findUserById(Long userId);
	
	public User findUserByUserName(String userName);
}
