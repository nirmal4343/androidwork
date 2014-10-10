package com.local.courier.model;


public class RegisterResponse extends Status{
	
	CustomerDynamicInfo customerDynamic;

	public CustomerDynamicInfo getCustomerDynamic() {
		return customerDynamic;
	}

	public void setCustomerDynamic(CustomerDynamicInfo customerDynamic) {
		this.customerDynamic = customerDynamic;
	}
	

}
