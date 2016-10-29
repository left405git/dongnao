/**
 * 
 */
package com.senvon.bank;

import com.senvon.bank.model.BankResponse;
import com.senvon.bank.model.OrderVO;

/**银行接口
 * @author senvon
 *
 */
public interface BankService {

	/**出款接口
	 * @param orderInfo
	 * @return
	 */
	public BankResponse moneyOut(OrderVO orderInfo);
}
