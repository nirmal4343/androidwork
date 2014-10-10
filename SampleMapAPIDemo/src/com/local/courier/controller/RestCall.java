package com.local.courier.controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonIOException;
import com.local.courier.config.Config;
import com.local.courier.controller.RestClient.RequestMethod;
import com.local.courier.model.CreditCardVO;
import com.local.courier.model.RegisterVO;
import com.local.courier.model.Status;
import com.local.courier.parser.Parser;




public class RestCall implements GetRESTData{

	private static final String TAG = "RestCall";

	private static RestCall instance = null;

	private RestCall() {
		// Exists only to defeat instantiation.
	}

	public static synchronized RestCall getInstance() {
		if(instance == null) {
			instance = new RestCall();
		}
		return instance;
	}


	public static String getResponseNew(String URL,JSONObject json,String uuid) {
		String response = null;
		//StringEntity se;
		RestClient client = new RestClient(URL);
		
		client.AddHeader("Content-type", "application/json");
		client.AddHeader("uuid", uuid);
		
		try {
			//CHANGED THE METHOD TO POST
			client.Execute(RequestMethod.POST,json);
		} catch (Exception e) {
			e.printStackTrace();
		}

		response = client.getResponse();

		return response;
	}



	@Override
	public Status getLoginInfo(String userName, String password) {
		JSONObject json = new JSONObject();

		try {
			
			json.put("key", "");
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String response = null;
		response = getResponseNew(Config.SERVER_URL, json,"<session id>");
		
		Status status = null;
		if(response != null){
			status = Parser.getInstance().parseLoginResp(response);
		}

		return status;
	}

	@Override
	public Status getRegistration(RegisterVO registerVo, CreditCardVO mCreditCardVo) {
		Status status = null;
		
		JSONObject json = new JSONObject();

		try {
			
			
			createRegisterRequest(json,registerVo,mCreditCardVo);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String response = null;
		response = getResponseNew(Config.REGISTER_URL, json,"");
		
		System.out.println(response);
		
		if(response != null){

			status = Parser.getInstance().parseRegistrationResp(response);
			
		}
		
		return status;
	}

	private void createRegisterRequest(JSONObject json, RegisterVO registerVo, CreditCardVO mCreditCardVo) throws JSONException{
		
		JSONObject jsonRequest = new JSONObject();

		jsonRequest.put("firstName", registerVo.getFirstName());
		jsonRequest.put("lastName", registerVo.getLastName());
		jsonRequest.put("email", registerVo.getEmailId());
		jsonRequest.put("phone", registerVo.getPhoneNo());
		jsonRequest.put("password", registerVo.getPassword());
		
		
		JSONObject jsonCreditC = new JSONObject();
		
		jsonCreditC.put("cardNumber", mCreditCardVo.getCreditCard() );
		jsonCreditC.put("month", mCreditCardVo.getMonth());
		jsonCreditC.put("year", mCreditCardVo.getYear());
		jsonCreditC.put("cvv", mCreditCardVo.getCvv());
		jsonCreditC.put("zip", mCreditCardVo.getZipCode());
		
		
		JSONArray location = new JSONArray();
		location.put(registerVo.getLongitude());
		location.put(registerVo.getLatitude());
      
		
		json.put("apiSignature", "12345");
		json.put("request", jsonRequest);
		json.put("creditcard", jsonCreditC);
		json.put("distance", 5.0);
		json.put("location", location);
		
	}
}
