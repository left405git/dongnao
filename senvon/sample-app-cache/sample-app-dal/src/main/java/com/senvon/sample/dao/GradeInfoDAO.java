package com.senvon.sample.dao;

import com.istock.base.ibatis.model.Page;
import com.senvon.sample.model.GradeInfo;
import com.senvon.sample.model.GradeInfoExample;
import java.util.List;

public interface GradeInfoDAO {
    int countByExample(GradeInfoExample example);

    int deleteByExample(GradeInfoExample example);

    int deleteByPrimaryKey(Integer id);

    Integer insert(GradeInfo record);

    Integer insertSelective(GradeInfo record);

    List<GradeInfo> selectByExample(GradeInfoExample example);

    GradeInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(GradeInfo record, GradeInfoExample example);

    int updateByExample(GradeInfo record, GradeInfoExample example);

    int updateByPrimaryKeySelective(GradeInfo record);

    int updateByPrimaryKey(GradeInfo record);

    List<GradeInfo> selectByPage(GradeInfoExample example, Page page);
}