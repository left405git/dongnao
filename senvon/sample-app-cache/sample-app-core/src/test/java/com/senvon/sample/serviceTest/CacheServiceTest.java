package com.senvon.sample.serviceTest;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.senvon.sample.service.GradeInfoService;

import junit.framework.Assert;

@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-cache.xml"})
public class CacheServiceTest {

	@Autowired
	private GradeInfoService service;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final int COUNT = 20000;
	//法令枪
	private CountDownLatch latch = new CountDownLatch(COUNT);
	
	private class ExecuteThread implements Runnable{
		public void run() {
			try {
				//运动员没上场,等人上场
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			service.queryWithInnerSynchronizedBlock();
		}
	}
	
	@Test
	public void test1() throws Exception{
		for(int i =0;i<COUNT;i++){
			//运动员上场了,上一个减一个
			Thread t = new Thread(new ExecuteThread());
			t.start();
			logger.info("====={}",i);
			latch.countDown();
		}
		Thread.sleep(6000);
		Integer count = service.getCount();
		logger.info("======{}",count);
		Assert.assertTrue(count == 1);
	}

	@Test
	public void testQuery() throws Exception {
		long start = System.currentTimeMillis();

		for(int i =0; i<COUNT; i++){
			//运动员上场了,上一个减一个
			Thread t = new Thread(new ExecuteThread());
			t.start();
			logger.info("======={}",i);
			latch.countDown();
		}

		long done = System.currentTimeMillis();
		logger.info("============耗时1>>{}",  (done - start));

		Thread.sleep(5000);
		Integer count = service.getCount();
		logger.info("======{}",count);

		done = System.currentTimeMillis();
		logger.info("============耗时2>>{}",  (done - start));
		Assert.assertTrue(count == 1);
	}

}
