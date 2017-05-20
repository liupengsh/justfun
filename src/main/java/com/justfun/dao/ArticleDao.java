package com.justfun.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.justfun.model.Article;

public interface ArticleDao {
	
	int insert(@Param("article") Article article);
	
	Article selectByPrimaryKey(@Param("id") Long id);
	
	int vote(@Param("id") Long id, @Param("votes") Integer votes);
    
    List<Article> selectAll();
    
}
