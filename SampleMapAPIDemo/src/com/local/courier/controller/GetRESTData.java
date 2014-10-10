package com.local.courier.controller;

import com.local.courier.model.CreditCardVO;
import com.local.courier.model.RegisterVO;
import com.local.courier.model.Status;



interface GetRESTData {

	public Status getLoginInfo(String userName, String password); 
	
	public Status getRegistration(RegisterVO registerVo, CreditCardVO mCreditCardVo);
	
}