package com.senvon.sample.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.whalin.MemCached.MemCachedClient;

/*
 * 缓存操作的抽象
 */
public class CacheTemplateService {

	@Autowired
	private MemCachedClient client;
	
	public <T> T findCache(String key , Date expire ,TypeReference<T> clazz, LoadCallback<T> load){
		String json = client.get(key)+"";
		if(StringUtils.isBlank(json)||json.equalsIgnoreCase("null")){
			//所有的线程,只要是等待在这边,都会去访问数据库
			synchronized(this){
				json = client.get(key)+"";
				if(StringUtils.isBlank(json)||json.equalsIgnoreCase("null")){
					T t = load.load();
					if(t != null){
						client.set(key, JSON.toJSONString(t), expire);
					}
					return t;
				}
				return JSON.parseObject(json , clazz);
			}
		}else{
			return JSON.parseObject(json , clazz);
		}
	}
}

