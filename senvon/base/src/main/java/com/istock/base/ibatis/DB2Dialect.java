/**
 * 
 */
package com.istock.base.ibatis;


/**
 * @author Senvon Shi
 *
 */
public class DB2Dialect implements Dialect {

	protected static final String SQL_END_DELIMITER = ";";
	public String getLimitString(String sql, int offset, int limit) {
		sql = trim(sql);
		int startOfSelect = sql.toLowerCase().indexOf("select");
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100).append(
				sql.substring(0, startOfSelect)).append("select * from ( select ").append(
				getRowNumber(sql));
		if (hasDistinct(sql)) {
			pagingSelect.append(" row_.* from ( ").append(sql.substring(startOfSelect)).append(
					" ) as row_");
		} else {
			pagingSelect.append(sql.substring(startOfSelect + 6));
		}
		pagingSelect.append(" ) as temp_ where rownumber_ ");
		if (offset > 0) {
			pagingSelect.append("between " + offset + " and " + (limit - 1));
		} else {
			pagingSelect.append("<= " + limit);
		}
		return pagingSelect.toString();
	}

	public String getCountSqlString(String sql) {
		if(sql != null && sql.length()>0){
			StringBuffer sb = new StringBuffer("select count(*) as "+RS_COUNT+" from ("+sql+") T_TEMP ");
			return sb.toString();
		}
		return null;
	}
	
	private String trim(String sql) {
		sql = sql.trim();
		if (sql.endsWith(SQL_END_DELIMITER)) {
			sql = sql.substring(0, sql.length() - 1 - SQL_END_DELIMITER.length());
		}
		return sql;
	}

	private static boolean hasDistinct(String sql) {
		return (sql.toLowerCase().indexOf("select distinct") >= 0);
	}

	private String getRowNumber(String sql) {
		StringBuffer rownumber = new StringBuffer(50).append("rownumber() over(");
		int orderByIndex = sql.toLowerCase().indexOf("order by");
		if ((orderByIndex > 0) && (!(hasDistinct(sql)))) {
			rownumber.append(sql.substring(orderByIndex));
		}
		rownumber.append(") as rownumber_,");
		return rownumber.toString();
	}
}
