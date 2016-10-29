/**
 * 
 */
package com.istock.base.enumation;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author SenVon
 *
 */
@Repository
public class EnumsDataProvider {

	@Resource(name="sqlMapClientTemplate")
	private SqlMapClientTemplate template;
	
	public List<BaseEnum> findEnumsData(){
		return (List<BaseEnum>)template.queryForList("BaseEnum.findAllEnum");
	}
}
