package com.senvon.firstApp.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.senvon.spring.dao.MenuInfoDAO;
import com.senvon.spring.model.MenuInfo;
import com.senvon.spring.model.MenuInfoExample;
import com.senvon.spring.page.Page;

@Repository
public class MenuInfoService {

	@Autowired
	private MenuInfoDAO menuInfoDao;
	
	public MenuInfo findMenuInfoById(Integer id){
		return menuInfoDao.selectByPrimaryKey(id);
	}
	
	public List<MenuInfo> findByName(String name , Page page){
		MenuInfoExample example = new MenuInfoExample();
		MenuInfoExample.Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(name)){
			criteria.andNameLike("%"+name+"%");
		}
		return menuInfoDao.selectByPage(example, page);
	}
	
	public Integer saveMenuInfo(MenuInfo menuInfo){
		if(menuInfo != null){
			if(menuInfo.getId() != null && menuInfo.getId()>0){
				//update
				return menuInfoDao.updateByPrimaryKeySelective(menuInfo);
			}else{
				//insert
				return menuInfoDao.insertSelective(menuInfo);
			}
		}
		return 0;
	}
	
	public Integer deleteMenuInfo(Integer id){
		if(id != null && id>0){
			return menuInfoDao.deleteByPrimaryKey(id);
		}
		return 0;
	}
}
