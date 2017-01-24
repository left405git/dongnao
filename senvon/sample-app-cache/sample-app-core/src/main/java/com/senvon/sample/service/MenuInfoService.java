package com.senvon.sample.service;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.senvon.sample.service.Template.CacheTemplate;
import com.senvon.sample.service.Template.TemplateCallback;
import com.whalin.MemCached.MemCachedClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.istock.base.ibatis.model.Page;
import com.senvon.sample.dao.MenuInfoDAO;
import com.senvon.sample.model.MenuInfo;
import com.senvon.sample.model.MenuInfoExample;

@Repository
public class MenuInfoService {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MenuInfoDAO menuInfoDao;
	@Autowired
	private MemCachedClient cachedClient;
	@Autowired
	private CacheTemplate cacheTemplate;

	public MenuInfo findMenuInfoById(Integer id) {
		return menuInfoDao.selectByPrimaryKey(id);
	}

	public List<MenuInfo> findByName(String name, Page page) {
		MenuInfoExample example = new MenuInfoExample();
		MenuInfoExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(name)) {
			criteria.andNameLike("%" + name + "%");
		}
		return menuInfoDao.selectByPage(example, page);
	}

	public Integer saveMenuInfo(MenuInfo menuInfo) {
		if (menuInfo != null) {
			if (menuInfo.getId() != null && menuInfo.getId() > 0) {
				//update
				return menuInfoDao.updateByPrimaryKeySelective(menuInfo);
			} else {
				//insert
				return menuInfoDao.insertSelective(menuInfo);
			}
		}
		return 0;
	}

	public Integer deleteMenuInfo(Integer id) {
		if (id != null && id > 0) {
			return menuInfoDao.deleteByPrimaryKey(id);
		}
		return 0;
	}

	public /*synchronized*/ List<MenuInfo> selectListByName(String name, Page page) {
		long start = System.currentTimeMillis();

		String key = "menuInfos";
		String jsonObj = cachedClient.get(key) + "";
		if (StringUtils.isBlank(jsonObj) || "null".equalsIgnoreCase(jsonObj)) {
			logger.info("=================no cache, 执行耗时{}===================", (System.currentTimeMillis() - start));
			MenuInfoExample example = new MenuInfoExample();
			MenuInfoExample.Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(name)) {
				criteria.andNameLike("%" + name + "%");
			}
			List<MenuInfo> menuInfos = menuInfoDao.selectByPage(example, page);

			cachedClient.set(key, JSON.toJSONString(menuInfos), DateUtils.addSeconds(new Date(), 3));

			return menuInfos;
		} else {
			logger.info("=================by cache，耗时{}===================", (System.currentTimeMillis() - start));
			return JSON.parseArray(jsonObj, MenuInfo.class);
		}

	}

	public List<MenuInfo> selectListByName2(String name, Page page) {
		long start = System.currentTimeMillis();

		String key = "menuInfos2";
		String jsonObj = cachedClient.get(key) + "";
		if (StringUtils.isBlank(jsonObj) || "null".equalsIgnoreCase(jsonObj)) {
			synchronized (this) {
				jsonObj = cachedClient.get(key) + "";
				if (StringUtils.isBlank(jsonObj) || "null".equalsIgnoreCase(jsonObj)) {

					MenuInfoExample example = new MenuInfoExample();
					MenuInfoExample.Criteria criteria = example.createCriteria();
					if (StringUtils.isNotBlank(name)) {
						criteria.andNameLike("%" + name + "%");
					}
					List<MenuInfo> menuInfos = menuInfoDao.selectByPage(example, page);

					logger.info("=================no cache, 执行耗时{}===================", (System.currentTimeMillis() - start));

					cachedClient.set(key, JSON.toJSONString(menuInfos), DateUtils.addSeconds(new Date(), 3));

					return menuInfos;
				} else {
					logger.info("=================by cache2===================");
					return JSON.parseArray(jsonObj, MenuInfo.class);
				}
			}
		} else {
			logger.info("=================by cache1===================");
			return JSON.parseArray(jsonObj, MenuInfo.class);
		}
	}

	public List<MenuInfo> selectListWithTemplate(final String name, final Page page) {
		String key = "menuInfos3";
		Date date = DateUtils.addSeconds(new Date(), 3);
		List<MenuInfo> result = cacheTemplate.query(key, date, new TypeReference<List<MenuInfo>>() {
		}, new TemplateCallback<List<MenuInfo>>() {
			@Override
			public List<MenuInfo> load() {
				MenuInfoExample example = new MenuInfoExample();
				MenuInfoExample.Criteria criteria = example.createCriteria();
				if (StringUtils.isNotBlank(name)) {
					criteria.andNameLike("%" + name + "%");
				}
				List<MenuInfo> menuInfos = menuInfoDao.selectByPage(example, page);
				return menuInfos;
			}
		});

		logger.info("使用Template方式查询：{}", JSON.toJSONString(result));

		return result;
	}
}
