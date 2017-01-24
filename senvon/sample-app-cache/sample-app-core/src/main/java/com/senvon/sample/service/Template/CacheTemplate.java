package com.senvon.sample.service.Template;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.whalin.MemCached.MemCachedClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by MG on 2017/1/24.
 */
public class CacheTemplate {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MemCachedClient cachedClient;

    public <T> T query(String key, Date expire, TypeReference<T> type, TemplateCallback<T> callback) {
        String jsonObj = cachedClient.get(key) + "";
        if (StringUtils.isBlank(jsonObj) || "null".equalsIgnoreCase(jsonObj)) {
            synchronized (this) {
                jsonObj = cachedClient.get(key) + "";
                if (StringUtils.isBlank(jsonObj) || "null".equalsIgnoreCase(jsonObj)) {
                    long start = System.currentTimeMillis();
                    T result = callback.load();
                    logger.info("=================no cache, 执行耗时{}===================", (System.currentTimeMillis() - start));
                    if (result != null) {
                        cachedClient.set(key, JSON.toJSONString(result), expire);
                    }

                    return result;
                } else {
                    logger.info("=================by cache2===================");
                    return JSON.parseObject(jsonObj, type);
                }
            }
        } else {
            logger.info("=================by cache1===================");
            return JSON.parseObject(jsonObj, type);
        }
    }
    
}
