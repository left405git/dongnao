/**
 * 
 */
package com.istock.base.ibatis.model;

import java.util.List;

/**
 * @author SenVon
 *
 */
@SuppressWarnings("rawtypes")
public class Page implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static int DEFAULT_COUNT = 20;
	
	private int pageSize = DEFAULT_COUNT;//current page records count
	private int pageNo = 1;//current page number
	private int total = 1;//total page number
	private int recordNumber;//the record number
	
	
	private List records;

	public Page(){}
	public Page(int count){
		this();
		setPageSize(count);
	}
	
	public Page(int current,int recordNumber){
		setPageNo(current);
		setTotal(recordNumber);
	}
	
	public Page(int count,int current,int recordNumber){
		setPageSize(count);
		setPageNo(current);
		setTotal(recordNumber);
	}
	
	public Page(int current,int recordNumber,List records){
		this(current,recordNumber);
		this.records = records;
	}
	
	public void setPageSize(int pageSize) {
		//this.count = DEFAULT_COUNT;
		if(pageSize > 0){
			this.pageSize = pageSize;
		}
	}
	
	public void setPageNo(int pageNo) {
		if(pageNo < 1){
			this.pageNo = 1;
		}
			this.pageNo = pageNo;
	}
	
	public void setTotal(int recordNumber) {
		if(recordNumber > 0)
		{
			int t = recordNumber/this.pageSize;
			this.total = recordNumber%this.pageSize!=0?++t:t;
			this.recordNumber = recordNumber;
		}
	}
	
	public int getPageNo(){
		return this.pageNo>this.total?this.total:this.pageNo;
	}
	
	public int getTotal(){
		return this.total;
	}
	
	public int getRecordNumber(){
		return this.recordNumber;
	}
	
	public int getPageSize(){
		return this.pageSize;
	}
	
	public int getPrePage(){
		return this.pageNo>2?this.pageNo-1:1;
	}

	public int getNextPage(){
		return this.pageNo<this.total?this.pageNo+1:this.total;
	}
	
	public int getStartIndex(){
		return (this.pageNo-1)*this.pageSize + 1;
	}
	
	public void setRecords(List obj){
		this.records = obj;
	}
	public List getRecords() {
		return records;
	}
}
