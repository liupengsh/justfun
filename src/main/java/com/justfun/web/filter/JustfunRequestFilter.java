package com.justfun.web.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.justfun.Constants;
import com.justfun.service.IUserService;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class JustfunRequestFilter extends PathMatchingFilter {

	@Autowired
	private IUserService userService;

	@Override
	protected boolean onPreHandle(ServletRequest req, ServletResponse response,
			Object mappedValue) throws Exception {

		HttpServletRequest request = (HttpServletRequest) req;
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
		request.setAttribute("BASEPATH", basePath);

		String userName = (String) SecurityUtils.getSubject().getPrincipal();
		req.setAttribute(Constants.CURRENT_USER, userService.findUserByUserName(userName));
		return true;
	}
}
