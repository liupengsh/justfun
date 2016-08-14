package com.justfun.dao;

import java.util.List;

import com.justfun.model.Resource;

public interface ResourceDao {
    int deleteByPrimaryKey(Long id);

    int insert(Resource record);

    int insertSelective(Resource record);

    Resource selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);
    
    List<Resource> selectAll();
}