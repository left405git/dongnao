package com.senvon.spring.dao;

import com.senvon.spring.model.MenuInfo;
import com.senvon.spring.model.MenuInfoExample;
import com.senvon.spring.page.Page;
import java.util.List;

public interface MenuInfoDAO {
    int countByExample(MenuInfoExample example);

    int deleteByExample(MenuInfoExample example);

    int deleteByPrimaryKey(Integer id);

    Integer insert(MenuInfo record);

    Integer insertSelective(MenuInfo record);

    List<MenuInfo> selectByExample(MenuInfoExample example);

    MenuInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(MenuInfo record, MenuInfoExample example);

    int updateByExample(MenuInfo record, MenuInfoExample example);

    int updateByPrimaryKeySelective(MenuInfo record);

    int updateByPrimaryKey(MenuInfo record);

    List<MenuInfo> selectByPage(MenuInfoExample example, Page page);
}