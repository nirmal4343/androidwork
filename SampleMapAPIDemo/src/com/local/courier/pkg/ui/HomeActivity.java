package com.local.courier.pkg.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.unbounded.android.locationapi.maps.R;



public class HomeActivity extends Activity {


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);

	}
	
	
	public void proceedLogin(View v) {
		
		Intent loginIntent = new Intent(this, LoginActivity.class);
		
		startActivity(loginIntent);
		
	}
	
	
	public void proceedRegistration(View v) {
		
		Intent loginIntent = new Intent(this, MainActivity.class);
		
		startActivity(loginIntent);
		
	}
	
}