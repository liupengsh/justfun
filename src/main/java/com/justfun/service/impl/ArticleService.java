package com.justfun.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justfun.dao.ArticleDao;
import com.justfun.model.Article;
import com.justfun.service.IArticleService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class ArticleService implements IArticleService {
	
	@Autowired
	private ArticleDao articleDao;

	@Override
	public int insert(Article article) {
		return articleDao.insert(article);
	}

	@Override
	public Article selectByPrimaryKey(Long id) {
		return articleDao.selectByPrimaryKey(id);
	}

	@Override
	public int vote(Long id, Integer votes) {
		return articleDao.vote(id, votes);
	}

	@Override
	public List<Article> selectAll() {
		return articleDao.selectAll();
	}
	
	
	
	@Autowired
	private JedisPool jedisPool;
	
	
    @Override
	public List<Map<String,String>> findRedisAll() {
    	
    	Jedis client = jedisPool.getResource();  
        try{  
            client.select(15);  
 
            Set<String> ids = client.zrevrange("score:", 0, -1);
            List<Map<String,String>> articles = new ArrayList<Map<String,String>>();
            for (String id : ids){
                Map<String,String> articleData = client.hgetAll(id);
                articleData.put("id", id);
                articles.add(articleData);
            }

            return articles;
            
        }catch(Exception e){  
            e.printStackTrace();  
        }finally{  
        	client.close();
        }  

        return null;
		
	}

}
