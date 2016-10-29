package com.senvon.firstApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.senvon.spring.dao.GradeInfoDAO;

@Repository
public class GradeInfoService {

	@Autowired
	private GradeInfoDAO gradeInfoDao;
}
