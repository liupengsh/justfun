package com.justfun.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.justfun.model.Article;
import com.justfun.service.IArticleService;

@Controller
@RequestMapping("article")
public class ArticleController {
	
	@Autowired
	private IArticleService articleService;
	
	@ResponseBody
	@RequestMapping("/findAll")
    public List<Article> findAll() {
		return articleService.selectAll();
    }
	
	@ResponseBody
	@RequestMapping("/findRedisAll")
	public List<Map<String,String>> findRedisAll() {
		return articleService.findRedisAll();
	}
	
	@ResponseBody
	@RequestMapping("/nothing")
	public String nothing() {
		return "1";
	}	
}
