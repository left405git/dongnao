package com.senvon.spring.page;

import java.sql.Connection;
import java.sql.SQLException;

import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.scope.StatementScope;

public class MySqlExecutor extends SqlExecutor {

	@Override
	public void executeQuery(StatementScope statementScope, Connection conn, String sql, Object[] parameters,
			int skipResults, int maxResults, RowHandlerCallback callback) throws SQLException {
		StringBuffer sb = new StringBuffer(sql);
		if(maxResults>0){
		    sb.insert(0, "SELECT * FROM (");
		    sb.append(") _temp LIMIT ").append(skipResults).append(",").append(maxResults);
		}
		super.executeQuery(statementScope, conn, sb.toString(), parameters, 0, 0, callback);
	}
	
}
