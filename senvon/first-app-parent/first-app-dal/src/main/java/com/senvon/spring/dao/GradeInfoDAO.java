package com.senvon.spring.dao;

import com.senvon.spring.model.GradeInfo;
import com.senvon.spring.model.GradeInfoExample;
import com.senvon.spring.page.Page;
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
    
    //待生成
    List<GradeInfo> selectByPage(GradeInfoExample example, Page page);
}