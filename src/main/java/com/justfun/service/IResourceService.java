package com.justfun.service;

import java.util.List;

import com.justfun.model.User;
import com.justfun.model.dto.Menu;

public interface IResourceService {

	public List<Menu> findMenus(User user);

}
