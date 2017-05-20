package com.justfun.service;

import java.util.List;
import java.util.Map;

import com.justfun.model.Article;

public interface IArticleService {
	
	public int insert(Article article);
	
	public Article selectByPrimaryKey(Long id);
	
	public int vote(Long id, Integer votes);
    
	public List<Article> selectAll();

	public List<Map<String, String>> findRedisAll();
	
}
