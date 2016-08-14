package com.justfun.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justfun.dao.ResourceDao;
import com.justfun.model.Resource;
import com.justfun.model.User;
import com.justfun.model.dto.Menu;
import com.justfun.service.IResourceService;

@Service
public class ResourceService implements IResourceService {
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Override
	public List<Menu> findMenus(User user) {
		List<Resource> resources = resourceDao.selectAll();
		return changeToMenus(resources);
	}
	
	/**
	 * 将Resource资源转化为menu
	 * @param resources
	 * @return
	 */
	private List<Menu> changeToMenus(List<Resource> resources) {
		
		List<Menu> lstRoot = new ArrayList<Menu>();		
		Iterator<Resource> lstOriginal = resources.iterator();
		
		while(lstOriginal.hasNext()) {
			Resource r = lstOriginal.next();
			
			// 删除为0的root资源
			if (0 == r.getParentId()) {
				lstOriginal.remove();
				continue;
			}
			
			// 为1的话，说明是一级菜单
			if (1 == r.getParentId()) {
				lstRoot.add(convertToMenu(r));
				lstOriginal.remove();
			}
		}
		
		traversalSonData(lstRoot, resources);		
		return lstRoot;
    }
	
	/**
	 * 递归将menu子节点转化出来
	 * @param lstRoot
	 * @param lstOriginal
	 */
	private void traversalSonData(List<Menu> lstRoot, List<Resource> lstOriginal) {
		// 此级的子将成下级的父
		List<Menu> lstParent = new ArrayList<Menu>();
		
		for (Menu parentMenu : lstRoot) {			
			for (Resource r : lstOriginal) {
				if (r.getParentId() == parentMenu.getId()) {
					Menu sonMenu = convertToMenu(r);
					parentMenu.getChildren().add(sonMenu);
					lstParent.add(sonMenu);
				}
			}
		}
		
		if (lstParent.size() > 0) {
			traversalSonData(lstParent, lstOriginal);
		}
	}
    
    private Menu convertToMenu(Resource resource) {
        return new Menu(resource.getId(), resource.getName(), resource.getIcon(), resource.getUrl());
    }
}
