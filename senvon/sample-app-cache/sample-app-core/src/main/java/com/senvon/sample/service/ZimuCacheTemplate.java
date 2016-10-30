package com.senvon.sample.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.whalin.MemCached.MemCachedClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by MG on 2016/10/30.
 */
public class ZimuCacheTemplate {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MemCachedClient client;

    public <T> T find(String key, Date expiry, TypeReference<T> clazz, QueryCallback<T> callback) {
        String json = client.get(key) + "";

        if (StringUtils.isBlank(json) || json.equalsIgnoreCase("null")) {
            synchronized (this) {
                json = client.get(key) + "";
                if (StringUtils.isBlank(json) || json.equalsIgnoreCase("null")) {
                    T t = callback.query();
                    if (t != null) {
                        client.set(key, JSON.toJSONString(t), expiry);
                    }
                    return t;
                } else {
                    logger.info("==================>>{}", json + "===========内：返回缓存");
                    return JSON.parseObject(json, clazz);
                }
            }
        }  else {
            logger.info("==================>>{}", json + "===========外：返回缓存");
            return JSON.parseObject(json, clazz);
        }
    }
}
