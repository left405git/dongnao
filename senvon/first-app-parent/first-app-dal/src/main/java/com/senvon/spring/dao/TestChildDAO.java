package com.senvon.spring.dao;

import com.senvon.spring.model.TestChild;
import com.senvon.spring.model.TestChildExample;
import com.senvon.spring.page.Page;
import java.util.List;

public interface TestChildDAO {
    int countByExample(TestChildExample example);

    int deleteByExample(TestChildExample example);

    int deleteByPrimaryKey(Integer id);

    Integer insert(TestChild record);

    Integer insertSelective(TestChild record);

    List<TestChild> selectByExample(TestChildExample example);

    TestChild selectByPrimaryKey(Integer id);

    int updateByExampleSelective(TestChild record, TestChildExample example);

    int updateByExample(TestChild record, TestChildExample example);

    int updateByPrimaryKeySelective(TestChild record);

    int updateByPrimaryKey(TestChild record);

    List<TestChild> selectByPage(TestChildExample example, Page page);
}