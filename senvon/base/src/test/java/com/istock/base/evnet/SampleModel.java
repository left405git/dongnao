package com.istock.base.evnet;

public class SampleModel extends PeopleModel{

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getFuck() {
		return fuck;
	}
	public void setFuck(String fuck) {
		this.fuck = fuck;
	}
	private String address;
	private String fuck;
	@Override
	public String toString() {
		return "SampleModel [address=" + address + ", fuck=" + fuck + "]";
	}
}
