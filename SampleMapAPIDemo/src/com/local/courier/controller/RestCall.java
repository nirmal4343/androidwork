package com.local.courier.controller;

import org.json.JSONException;
import org.json.JSONObject;

import com.local.courier.config.Config;
import com.local.courier.controller.RestClient.RequestMethod;
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


	public static String getResponseNew(String URL,JSONObject json,String sessionId /*HashMap<String, String> map*/) {
		String response = null;
		//StringEntity se;
		RestClient client = new RestClient(URL);
		
		client.AddHeader("Content-type", "application/json");
		client.AddHeader("SessionId", sessionId);
		
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
	public Status getRegistration(RegisterVO registerVo) {
		Status status = null;
		
		JSONObject json = new JSONObject();

		try {
			
			json.put("apiSignature", "12345");
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String response = null;
		response = getResponseNew(Config.REGISTER_URL, json,"");
		
		
		if(response != null){

			status = Parser.getInstance().parseLoginResp(response);
			
		}
		
		return status;
	}
}
