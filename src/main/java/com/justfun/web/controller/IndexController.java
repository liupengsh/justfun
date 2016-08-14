package com.justfun.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.justfun.model.User;
import com.justfun.model.dto.Menu;
import com.justfun.service.IResourceService;
import com.justfun.web.bind.annotation.CurrentUser;

@Controller
public class IndexController {
	
	@Autowired
	private IResourceService resourceService;
	
	@RequestMapping("/")
    public String index(@CurrentUser User loginUser, Model model) {
		List<Menu> menus = resourceService.findMenus(loginUser);
        model.addAttribute("menus", menus);        
        return "index";
    }
	
    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
}
