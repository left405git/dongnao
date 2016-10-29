/**
 * 
 */
package com.istock.base.ibatis;

/**
 * @author SenVon
 *
 */
public interface Dialect {
	public static final String RS_COUNT = "rs_count";

	/**分页语句的数据库方言
	 * @param sql
	 * @param offset
	 * @param limit
	 * @return
	 */
	public String getLimitString(String sql, int offset, int limit);

	/**count语句的数据库方言
	 * @param sql
	 * @return
	 */
	public String getCountSqlString(String sql);
	
}
