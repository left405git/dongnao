package com.senvon.sample.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.whalin.MemCached.MemCachedClient;

@Repository
public class GradeInfoService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ZimuCacheTemplate zimuCacheTemplate;
	@Autowired
	private MemCachedClient client;

	private Integer count = 0;

	public Integer getCount() {
		return count;
	}

	@Autowired
	private CacheTemplateService cacheTemplate;

	public Map<String, Object> query1() {
		String key = "senvon";
		Date expire = DateUtils.addSeconds(new Date(), 3);

		Map<String, Object> cache = cacheTemplate.findCache(key, expire, new TypeReference<Map<String, Object>>() {
		}, new LoadCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> load() {
				//缓存没找到,假设查询数据库花费的时间 
				try {
					logger.info("=======================no cache");
					++count;
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("name", "senvon");
				result.put("age", 12);
				return result;
			}

		});
		logger.info("==================cache:{}", JSON.toJSONString(cache));
		return cache;
	}

	public /*synchronized*/ Map<String, Object> query() {
		String key = "senvon";
		String json = client.get(key) + "";
		if (StringUtils.isBlank(json) || json.equalsIgnoreCase("null")) {
			//所有的线程,只要是等待在这边,都会去访问数据库
			synchronized (this) {
				json = client.get(key) + "";
				if (StringUtils.isBlank(json) || json.equalsIgnoreCase("null")) {
					//缓存没找到,假设查询数据库花费的时间 
					//抽象begin
					try {
						logger.info("======================no cache");
						++count;
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Map<String, Object> result = new HashMap<String, Object>();
					result.put("name", "senvon");
					result.put("age", 12);
					//抽象end

					json = JSON.toJSONString(result);
					client.set(key, json, DateUtils.addSeconds(new Date(), 3));
					return result;
				} else {
					return JSON.parseObject(json);
				}
			}
		} else {
			return JSON.parseObject(json);
		}
	}

	public synchronized Map<String, Object> queryNoLock() {
		String key = "zimu";
		String json = client.get(key) + "";

		if (StringUtils.isBlank(json) || json.equalsIgnoreCase("null")) {
			logger.info("==================>>{}", json + "：缓存为空，查询数据库");
			try {
				++count;
				logger.info("==================count>>{}", count);
				Thread.sleep(300);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			Map<String, Object> result = new HashMap<>();
			result.put("name", "zimu");
			result.put("age", 18);
			client.set(key, JSON.toJSONString(result), DateUtils.addSeconds(new Date(), 1));
			logger.info("==================>>{}", "返回查询" + result.toString());
			return result;
		}  else {
			logger.info("==================>>{}", json + "：返回缓存");
			return JSON.parseObject(json);
		}
	}

	public Map<String, Object> queryWithInnerSynchronizedBlock() {
		String key = "zimu";
		String json = client.get(key) + "";

		if (StringUtils.isBlank(json) || json.equalsIgnoreCase("null")) {
			synchronized (this) {
				json = client.get(key) + "";
				if (StringUtils.isBlank(json) || json.equalsIgnoreCase("null")) {

					logger.info("==================>>{}", json + "：缓存为空，查询数据库");
					try {
						++count;
						logger.info("==================count>>{}", count);
						Thread.sleep(300);
					} catch (InterruptedException e) {
					}
					Map<String, Object> result = new HashMap<>();
					result.put("name", "zimu");
					result.put("age", 18);
					client.set(key, JSON.toJSONString(result), DateUtils.addSeconds(new Date(), 3));
					logger.info("==================>>{}", "返回查询" + result.toString());

					return result;
				} else {
					logger.info("==================>>{}", json + "===========内：返回缓存");
					return JSON.parseObject(json);
				}
			}
		}  else {
			logger.info("==================>>{}", json + "===========外：返回缓存");
			return JSON.parseObject(json);
		}
	}


	public Map<String, Object> queryWithTemplate() {
		String key = "zimu";
		Date expiry = DateUtils.addSeconds(new Date(), 3);

		return zimuCacheTemplate.find(key, expiry, new TypeReference<Map<String, Object>>() {
		}, new QueryCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> query() {
				logger.info("==================>>缓存为空，查询数据库");
				try {
					++count;
					logger.info("==================count>>{}", count);
					Thread.sleep(300);
				} catch (InterruptedException e) {
				}
				Map<String, Object> result = new HashMap<>();
				result.put("name", "zimu");
				result.put("age", 18);
				logger.info("==================>>{}", "返回查询" + result.toString());
				return result;
			}
		});
	}

}
