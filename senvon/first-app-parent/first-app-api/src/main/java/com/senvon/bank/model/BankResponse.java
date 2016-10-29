package com.senvon.bank.model;

/**银行返回对象
 * @author senvon
 *
 */
public class BankResponse {

	private String code;
	private String message;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
