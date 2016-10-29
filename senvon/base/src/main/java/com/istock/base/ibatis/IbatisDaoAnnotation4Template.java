package com.istock.base.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.istock.base.ibatis.model.Page;

@SuppressWarnings("rawtypes")
public abstract class IbatisDaoAnnotation4Template {

	public abstract IbatisTemplate getSqlMapClientTemplate();
	
	public List searchListPageByMap(String sqlId,Map<String , Object> parameters , Page page) throws DataAccessException{
		
		return this.searchListPageMyObject(sqlId, parameters, page);
	}
	
	public List searchListPageMyObject(String sqlId,Object obj,Page page) throws DataAccessException{
		List result = null;
		if(page == null){
			page = new Page();
			result =getSqlMapClientTemplate().queryForList(sqlId, obj, SqlExecutor.NO_SKIPPED_RESULTS, SqlExecutor.NO_MAXIMUM_RESULTS);
		}else{
			result = getSqlMapClientTemplate().queryForList(sqlId, obj, page.getStartIndex(), page.getPageSize());
		}
		
		page.setRecords(result);
		page.setTotal(((PaginationSqlExecutor)getSqlMapClientTemplate().getSqlExecutor()).getRecordCount()!=null?((PaginationSqlExecutor)getSqlMapClientTemplate().getSqlExecutor()).getRecordCount().intValue():0 );
		
		return result;
	}
}
