package com.senvon.sample.serviceTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.istock.base.ibatis.model.Page;
import com.senvon.sample.model.MenuInfo;
import com.senvon.sample.service.MenuInfoService;

@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-test.xml"})
//@TransactionConfiguration(defaultRollback = true)
@Transactional
public class MenuInfoTest {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MenuInfoService menuInfoService;

	@Test
	public void test1() throws Exception{
		logger.info("===========================================");

		String name = "$senvon$";

		MenuInfo menuInfo = new MenuInfo();
		menuInfo.setMemo("senvonTest");
		menuInfo.setName(name);
		menuInfoService.saveMenuInfo(menuInfo);

		List<MenuInfo> menuList = menuInfoService.findByName(name, new Page());
		logger.info("menuList is :{}" , menuList);
		Assert.assertTrue(menuList != null);
		Assert.assertTrue(menuList.size() == 1);

		Integer menuId = menuList.get(0).getId();
		menuInfoService.deleteMenuInfo(menuId);

		List<MenuInfo> menu2List = menuInfoService.findByName(name, new Page());
		logger.info("menuList2 is :{}" , menu2List);
		Assert.assertTrue(menu2List != null);
		Assert.assertTrue(menu2List.size() == 0);

	}

	@Test
	public void testList() throws Exception {
		List<MenuInfo> menuInfos = menuInfoService.selectListByName("zimu", new Page());
		logger.info("menu info List by name is : {}", menuInfos);
		Assert.assertTrue(menuInfos != null);
		Assert.assertTrue(menuInfos.size() > 0);
	}

	private static final  int COUNT = 30;

	CountDownLatch latch = new CountDownLatch(COUNT);

	class ExecuteThread implements Runnable {
		@Override
		public void run() {
			menuInfoService.selectListByName2("zimu", new Page());
			latch.countDown();
		}
	}

	@Test
	public void testListByThread() throws Exception {
		long start = System.currentTimeMillis();

		for (int i = 0; i<=COUNT; i++) {
			Thread thread = new Thread(new ExecuteThread());
			thread.start();
		}
		latch.await();

		logger.info("执行耗时：{}", (System.currentTimeMillis() - start));
		Thread.sleep(2000);
	}
}
