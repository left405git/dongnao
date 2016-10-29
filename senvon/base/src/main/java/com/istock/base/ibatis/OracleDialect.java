/**
 * 
 */
package com.istock.base.ibatis;



/**
 * @author Senvon Shi
 *
 */
public class OracleDialect implements Dialect {

	/**
SELECT * FROM 
(
SELECT A.*, ROWNUM RN 
FROM (SELECT * FROM TABLE_NAME) A 
WHERE ROWNUM <= 40
)
WHERE RN >= 21
	 * 
	 */
	public String getLimitString(String sql, int offset, int limit) {
		if(sql != null && sql.length()>0){
			if(limit>0){
				StringBuffer sb = new StringBuffer();
				sb.append("SELECT * from ( SELECT T_TEMP.*, ROWNUM RN FROM (").append(sql).append(") T_TEMP" +
						" WHERE ROWNUM < "+(offset+limit)+
						" ) WHERE RN >= "+(offset>0?offset:0));
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
}
