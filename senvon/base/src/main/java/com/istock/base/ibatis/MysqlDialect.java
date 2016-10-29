/**
 * 
 */
package com.istock.base.ibatis;


/**
 * @author SenVon
 *mysql db dialect implement
 */
public class MysqlDialect implements Dialect {

	
	public String getCountSqlString(String sql) {
		if(sql != null && sql.length()>0){
			StringBuffer sb = new StringBuffer("select count(*) as "+RS_COUNT+" from ("+sql+") T_TEMP ");
			return sb.toString();
		}
		return null;
	}

	public String getLimitString(String sql, int offset, int limit) {
		if(sql != null && sql.length()>0){
			if(limit>0){
				StringBuffer sb = new StringBuffer();
				sb.append("select * from (").append(sql).append(") T_TEMP");
				if(sql.toUpperCase().indexOf("ORDER BY")<0){
					sb.append(" order by 1 ");
				}
				sb.append(" limit "+(offset-1>0?offset-1:0)+","+limit);
				return sb.toString();
			}
			return sql;
		}
		return null;
	}
}
