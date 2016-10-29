package com.senvon.firstapp.dal.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.senvon.spring.dao.ExtGradeInfoDAO;
import com.senvon.spring.model.GradeInfo;
import com.senvon.spring.page.Page;

@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-test.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class AnotherDaoTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ExtGradeInfoDAO gradeInfoDao;
	
	@Test
	public void test1() throws Exception{
		List<GradeInfo> gradeList = gradeInfoDao.findExtGradeInfo(2L, new Page());
		logger.info("gradeList:{}" , gradeList);
	}
}
