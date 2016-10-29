/**
 * 
 */
package com.istock.base.ibatis;

import java.lang.reflect.Field;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.util.ReflectionUtils;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;

/**
 * @author shishengfeng.senvon
 *
 */
@SuppressWarnings("deprecation")
@Resource
public class IbatisTemplate extends SqlMapClientTemplate {

	@Autowired
	private SqlExecutor sqlExecutor;
	
	public SqlExecutor getSqlExecutor(){
		return this.sqlExecutor;
	}
	
	@Override
	public void afterPropertiesSet() {
		SqlMapClient sqlMapClient = this.getSqlMapClient();
		if (sqlMapClient instanceof ExtendedSqlMapClient) {
			//FieldUtils.writeField(sqlMapClient, "sqlExecutor", sqlExecutor , true);
			//FieldUtils.writeField(((ExtendedSqlMapClient) sqlMapClient).getDelegate(), "sqlExecutor", sqlExecutor , true);
//			ClassUtil.setFieldValue(((ExtendedSqlMapClient) sqlMapClient).getDelegate(), "sqlExecutor", SqlExecutor.class,sqlExecutor);
			
			Field field = ReflectionUtils.findField(SqlMapExecutorDelegate.class, "sqlExecutor");
			ReflectionUtils.makeAccessible(field);
			ReflectionUtils.setField(field, ((ExtendedSqlMapClient) sqlMapClient).getDelegate(), sqlExecutor);
		}
	}
}
