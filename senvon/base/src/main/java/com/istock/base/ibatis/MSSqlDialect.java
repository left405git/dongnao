/**
 * 
 */
package com.istock.base.ibatis;


/**
 * @author Senvon Shi
 *
 */
public class MSSqlDialect implements Dialect {

	private static final String SELECT = "select ";
	private static final String FROM = "from ";
	private static final String DISTINCT = "distinct ";
	/**
	 * the sql is : select * from user_table
	 * select TOP limit * FROM ( 
	 * SELECT ROW_NUMBER() OVER (ORDER BY id) AS rownumber_,  * from user_table
	 * ) A WHERE A.rownumber_ > offset
	 */
	public String getLimitString(String sql, int offset, int limit) {
		if(sql != null && sql.length()>0){
			if(limit>0){
				StringBuilder sb = new StringBuilder(sql.trim().toUpperCase());

				int orderByIndex = sb.indexOf("order by");
				CharSequence orderby = orderByIndex > 0 ? sb.subSequence(orderByIndex, sb.length())
						: "ORDER BY CURRENT_TIMESTAMP";

				if (orderByIndex > 0) {
					sb.delete(orderByIndex, orderByIndex + orderby.length());
				}

				replaceDistinctWithGroupBy(sb);

				insertRowNumberFunction(sb, orderby);

				sb.insert(0, "WITH query AS (").append(") SELECT * FROM query ");
				sb.append("WHERE T_TEMP BETWEEN "+offset+" AND "+(limit+offset));

				return sb.toString();
			}
			return sql;
		}
		return null;
	}

	public String getCountSqlString(String sql) {
		if(sql != null && sql.length()>0){
			StringBuffer sb = new StringBuffer("select count(*) as "+RS_COUNT+" from ("+sql+") T_TEMP ");
			return sb.toString();
		}
		return null;
	}

	private void replaceDistinctWithGroupBy(StringBuilder sql) {
		int distinctIndex = sql.indexOf(MSSqlDialect.DISTINCT);
		if (distinctIndex > 0) {
			sql.delete(distinctIndex, distinctIndex + MSSqlDialect.DISTINCT.length() + 1);
			sql.append(" group by").append(getSelectFieldsWithoutAliases(sql));
		}
	}
	
	private CharSequence getSelectFieldsWithoutAliases(StringBuilder sql) {
		String select = sql.substring(sql.indexOf(MSSqlDialect.SELECT) + MSSqlDialect.SELECT.length(), sql.indexOf(MSSqlDialect.FROM));

		return stripAliases(select);
	}
	
	private String stripAliases(String str) {
		return str.replaceAll("\\sas[^,]+(,?)", "$1");
	}
	
	private void insertRowNumberFunction(StringBuilder sql, CharSequence orderby) {
		// Find the end of the select statement
		int selectEndIndex = sql.indexOf(MSSqlDialect.SELECT) + MSSqlDialect.SELECT.length();

		// Insert after the select statement the row_number() function:
		sql.insert(selectEndIndex, " ROW_NUMBER() OVER (" + orderby + ") as T_TEMP,");
	}

}
