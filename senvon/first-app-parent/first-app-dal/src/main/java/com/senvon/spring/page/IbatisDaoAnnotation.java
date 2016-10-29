package com.senvon.spring.page;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.ibatis.sqlmap.engine.execution.SqlExecutor;

@SuppressWarnings("rawtypes")
@Resource
public class IbatisDaoAnnotation {

	@Autowired
	private IbatisTemplate sqlMapClientTemplate;
	
	public IbatisTemplate getSqlMapClientTemplate(){
		return this.sqlMapClientTemplate;
	}
	
	public void setSqlMapClientTemplate(IbatisTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
	
	public List searchListPageMyObject(String sqlId,Object obj,Page page) throws DataAccessException{
		List result = null;
		if(page == null){
			page = new Page();
			result =sqlMapClientTemplate.queryForList(sqlId, obj, SqlExecutor.NO_SKIPPED_RESULTS, SqlExecutor.NO_MAXIMUM_RESULTS);
		}else{
			result = sqlMapClientTemplate.queryForList(sqlId, obj, page.getStartIndex(), page.getPageSize());
		}
		
		page.setRecords(result);
		page.setTotal(
				((PaginationSqlExecutor)sqlMapClientTemplate.getSqlExecutor()).getRecordCount()!=null
				?((PaginationSqlExecutor)sqlMapClientTemplate.getSqlExecutor()).getRecordCount().intValue()
						:0 );
		
		return result;
	}
}
