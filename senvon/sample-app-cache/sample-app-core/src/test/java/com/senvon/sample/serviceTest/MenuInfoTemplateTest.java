package com.senvon.sample.serviceTest;

import com.istock.base.ibatis.model.Page;
import com.senvon.sample.model.MenuInfo;
import com.senvon.sample.service.MenuInfoService;
import com.whalin.MemCached.MemCachedClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-test.xml"})
//@TransactionConfiguration(defaultRollback = true)
@Transactional
public class MenuInfoTemplateTest {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MenuInfoService menuInfoService;

	@Autowired
	private MemCachedClient cachedClient;

	private static final  int COUNT = 30;

	CountDownLatch latch = new CountDownLatch(COUNT);

	class QueryRunnable implements Runnable {
		@Override
		public void run() {
			menuInfoService.selectListWithTemplate("zimu", null);
			latch.countDown();
		}
	}

	@Before
	public void init() {
		logger.info("删除key：menuInfos3");
		cachedClient.delete("menuInfos3");
	}

	@Test
	public void testListWithTemplate() throws Exception {
		long start = System.currentTimeMillis();
		ExecutorService executor = Executors.newFixedThreadPool(COUNT);

		for (int i = 0; i<=COUNT; i++) {
			executor.execute(new QueryRunnable());
		}
		latch.await();

		logger.info("执行耗时：{}", (System.currentTimeMillis() - start));
		Thread.sleep(2000);
	}

}
