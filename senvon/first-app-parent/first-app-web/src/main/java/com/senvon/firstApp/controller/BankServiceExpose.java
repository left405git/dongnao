package com.senvon.firstApp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.senvon.bank.BankService;
import com.senvon.bank.model.BankResponse;
import com.senvon.bank.model.OrderVO;

@Controller
public class BankServiceExpose implements BankService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	@RequestMapping("moneyOut")
	public @ResponseBody BankResponse moneyOut(OrderVO orderInfo) {
		logger.info("================moneyOut==========:{}" , orderInfo);
		try {
			Thread.currentThread().sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		BankResponse result = new BankResponse();
		result.setCode("S");
		result.setMessage("交易成功");
		logger.info("================moneyOut==========:{}" , result);
		return result;
	}

}
