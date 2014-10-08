package com.local.courier.parser;

import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

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
}
