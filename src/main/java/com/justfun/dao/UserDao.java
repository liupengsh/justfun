package com.justfun.dao;

import com.justfun.model.User;

public interface UserDao {
	
	public User findUserById(Long userId);
	
}
