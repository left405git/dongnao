package com.senvon.bank.model;

import java.math.BigDecimal;

public class OrderVO {

	private String id;//订单号,唯一
	private BigDecimal amount;//金额,单位分
	private String name;//其他属性
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderVO [id=");
		builder.append(id);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
}
