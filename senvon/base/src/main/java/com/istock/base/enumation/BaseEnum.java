/**
 * 
 */
package com.istock.base.enumation;

import java.io.Serializable;

/**
 * @author SenVon
 *
 */
public class BaseEnum implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer value;
	private String team;
	private String code;
	private String description;
	private Integer orderby;
	
	public Integer getOrderby() {
		return orderby;
	}

	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}

	public BaseEnum(){
		super();
	}
	
	public BaseEnum(Integer value,String team,String code,String description){
		this();
		setValue(value);
		setTeam(team);
		setCode(code);
		setDescription(description);
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "BaseEnum [id=" + id + ", value=" + value + ", team=" + team
				+ ", code=" + code + ", description=" + description
				+ ", orderby=" + orderby + "]";
	}
}
