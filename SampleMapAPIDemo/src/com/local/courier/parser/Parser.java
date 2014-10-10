package com.local.courier.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.local.courier.model.Courier;
import com.local.courier.model.CustomerDynamicInfo;
import com.local.courier.model.RegisterResponse;
import com.local.courier.model.Status;


public class Parser {

	private static final String TAG = "Parser";

	private static Parser instance = null;

	private Parser() {
		// Exists only to defeat instantiation.
	}

	public static synchronized Parser getInstance() {
		if(instance == null) {
			instance = new Parser();
		}
		return instance;
	}

	
	/**
	 * To Parse Login Response
	 * @param jsonString
	 * @return Status - Object to return
	 */
	
	public Status parseLoginResp(String jsonString){

		Status status = new Status();
		
		try{
			JSONObject json = new JSONObject(jsonString);
			Log.d(TAG, "json ="+json);
			
			status.setStatus(json.getString("status"));
			status.setMessage(json.getString("statusMessage"));


			Log.i(TAG, "Status = " + status.getStatus());
			Log.i(TAG, "SMsg = " +status.getMessage());

		}catch (JSONException e) {
			e.printStackTrace();
			status = null;
		}catch(NullPointerException e){
			e.printStackTrace();
			status = null;
		}
		return status;
	}
	
	public Status parseRegistrationResp(String jsonString){

		RegisterResponse resp = new RegisterResponse();
		
		try{
			JSONObject json = new JSONObject(jsonString);
			Log.d(TAG, "json ="+json);
			
			JSONArray result = json.getJSONObject("results").getJSONObject("customerDynamicInfo").getJSONArray("couriers");
			
			parseCustomerDymanic(result,resp);

		}catch (JSONException e) {
			e.printStackTrace();
			resp = null;
		}catch(NullPointerException e){
			e.printStackTrace();
			resp = null;
		}
		return resp;
		
	}

	private void parseCustomerDymanic(JSONArray result, RegisterResponse resp) {

		if (result.length() > 0) {
			CustomerDynamicInfo customerDynamicInfo = new CustomerDynamicInfo();
			ArrayList<Courier> courierList = new ArrayList<Courier>();

			for (int g = 0; g < result.length() ; g++) {
				
				Courier courier = new Courier();
				try {

					JSONObject packageObj = result.getJSONObject(g);

					courier.setUuid(packageObj.getString("uuid"));
					JSONArray locList = packageObj.getJSONArray("location");
					courier.setLongitude(locList.getDouble(0));
					courier.setLattitude(locList.getDouble(1));
					courierList.add(courier);

				} catch (JSONException e) {

					e.printStackTrace();
				}
				customerDynamicInfo.setCourierList(courierList);
				resp.setCustomerDynamic(customerDynamicInfo);

			}
		}

	}
}
