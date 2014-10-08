package com.local.courier.controller;

import com.local.courier.model.Status;



interface GetRESTData {

	public Status getLoginInfo(String userName, String password); 
	
	public Status getRegistration(String fName, String lName, String phone, String email);
	
}