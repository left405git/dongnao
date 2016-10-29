/**
 * 
 */
package com.istock.base.ibatis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.scope.StatementScope;

/**
 * @author SenVon
 *
 */
public class PaginationSqlExecutor extends SqlExecutor {

	public enum DB_Type{MYSQL,DB2,ORACLE,MSSQL}
	private Dialect dialect ;
	private ThreadLocal<Long> recordCount = new ThreadLocal<Long>();

	private DB_Type type;
	public void setType(DB_Type type){
		this.type = type;
	}
	public Dialect getDialect() {
		return dialect;
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	public void setRecordCount(Long recordCount){
		this.recordCount.set(recordCount);
	}
	
	public Long getRecordCount(){
		return this.recordCount.get();
	}
	
	public void init(){
		if(this.dialect == null){
			if(this.type != null){
				if(this.type == DB_Type.MYSQL){
					setDialect(new MysqlDialect());
				}else if(this.type == DB_Type.ORACLE){
					setDialect(new OracleDialect());
				}else if(this.type == DB_Type.DB2){
					setDialect(new DB2Dialect());
				}else if(this.type == DB_Type.MSSQL){
					setDialect(new MSSqlDialect());
				}
			}
		}
	}
	
	@Override
	public void executeQuery(StatementScope scope, Connection conn, String sql, Object[] parameters, int offset, int limit, RowHandlerCallback callback) throws SQLException {
		String limitSql = sql;
		if(dialect != null){
			
			if(limit>0){
				String countSql = dialect.getCountSqlString(sql);
				if(conn != null){
					
					ResultSet rs = null;
					PreparedStatement pstmt = null;
					try{
						pstmt = conn.prepareStatement(countSql);
						if(parameters!=null){
		                  /*for(int i=0;i<parameters.length;i++){
		                	  pstmt.setObject(i+1, parameters[i]);
	//	                	  paramBuf.append(parameters[i].toString()+"\t");
		                  }*/
		                  scope.getParameterMap().setParameters(scope, pstmt, parameters);
		                  
		                  rs = pstmt.executeQuery();
		                  Long recordNumber = new Long(0);
		                  if(rs != null){
		                	  while(rs.next()){
		                		  recordNumber = rs.getLong(1);
		                	  }
		                  }
		                  setRecordCount(recordNumber);
						}
					}catch(Exception e){
					}finally{
						if(rs != null){rs.close();}
//						if(pstmt != null){pstmt.close();}
					}
				}
				limitSql = dialect.getLimitString(limitSql, offset, limit);
			}
			
		}
		super.executeQuery(scope, conn, limitSql, parameters, NO_SKIPPED_RESULTS, NO_MAXIMUM_RESULTS, callback);
	}
	

}
