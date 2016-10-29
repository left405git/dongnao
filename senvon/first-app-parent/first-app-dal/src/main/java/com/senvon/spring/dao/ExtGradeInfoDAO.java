package com.senvon.spring.dao;

import java.util.List;

import com.senvon.spring.model.GradeInfo;
import com.senvon.spring.page.Page;

public interface ExtGradeInfoDAO {

	public List<GradeInfo> findExtGradeInfo(Long studentId , Page page);
}
