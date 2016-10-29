package com.istock.base.event;

import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

public class Event extends EventObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final long timestamp;
	private String name;
	private Map<String , Object> context;//事件上下文,保存处理结果的中间变量
	
	public Event(Object source) {
		super(source);
		timestamp = System.currentTimeMillis();
		context = new HashMap<String , Object>();
	}
	
	public Event(Object source , String name){
		this(source);
		this.name = name;
	}
	
	public Long getTimestamp(){
		return this.timestamp;
	}
	public String getName(){
		return this.name;
	}

	public Map<String, Object> getContext() {
		return context;
	}
}
