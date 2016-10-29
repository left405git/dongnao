package com.senvon.sample.dao;

import com.istock.base.ibatis.model.Page;
import com.senvon.sample.model.MenuInfo;
import com.senvon.sample.model.MenuInfoExample;
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