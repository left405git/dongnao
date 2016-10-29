package com.istock.base.evnet;

public class PeopleModel {

	private String name;
	private Integer age;
	private Integer sex;//1:男,2:女
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	@Override
	public String toString() {
		return "PeopleModel [name=" + name + ", age=" + age + ", sex=" + sex
				+ "]";
	}
}
