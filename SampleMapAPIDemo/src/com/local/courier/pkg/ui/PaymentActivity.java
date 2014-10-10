package com.local.courier.pkg.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.local.courier.controller.RestCall;
import com.local.courier.model.CreditCardVO;
import com.local.courier.model.RegisterVO;
import com.unbounded.android.locationapi.maps.AlertDialogManager;
import com.unbounded.android.locationapi.maps.ConnectionDetector;
import com.unbounded.android.locationapi.maps.GPSTracker;
import com.unbounded.android.locationapi.maps.GooglePlaces;
import com.unbounded.android.locationapi.maps.PlacesList;
import com.unbounded.android.locationapi.maps.R;

public class PaymentActivity extends Activity {

	// Button
	Button btnRegister;
	// flag for Internet connection status
	Boolean isInternetPresent = false;
	// Connection detector class
	ConnectionDetector cd;
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();
	// Google Places
	GooglePlaces googlePlaces;
	// Places List
	PlacesList nearPlaces;
	// GPS Location
	GPSTracker gps;
	// Button
	Button btnNext;
	// Progress dialog
	ProgressDialog pDialog;
	// Places Listview
	ListView lv;
	// ListItems data
	ArrayList<HashMap<String, String>> placesListItems = new ArrayList<HashMap<String, String>>();

	RegisterVO mRegisterVo;

	CreditCardVO mCreditCardVo;
	
	private EditText creditCard;
	private EditText month;
	private EditText year;
	private EditText cvv;
	private EditText zipCode;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		mRegisterVo = (RegisterVO)getIntent().getParcelableExtra(RegisterActivity.OBJECT_KEY);
		 
		creditCard = (EditText) findViewById(R.id.credit_card);
		month = (EditText) findViewById(R.id.month);
		year = (EditText) findViewById(R.id.year);
		cvv = (EditText) findViewById(R.id.cvv_number);
		zipCode = (EditText) findViewById(R.id.zip_code);
		
		 
		// Check if Internet present
		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();
		if (!isInternetPresent) {
			// Internet Connection is not present
			alert.showAlertDialog(PaymentActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}

		// creating GPS Class object
		gps = new GPSTracker(PaymentActivity.this);

		// check if GPS location can get
		if (gps.canGetLocation()) {
			Log.d("Your Location", "latitude:" + gps.getLatitude()
					+ ", longitude: " + gps.getLongitude());
			
			mRegisterVo.setLongitude(gps.getLongitude());
			mRegisterVo.setLatitude(gps.getLongitude());
			
		} else {
			// Can't get user's current location
			alert.showAlertDialog(PaymentActivity.this, "GPS Status","Couldn't get location information. Please enable GPS",false);
			// stop executing code by return
			return;
		}

		
		// button show on map
		btnRegister = (Button) findViewById(R.id.registerBtn);

		/** Button click event for shown on map */
		btnRegister.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				new RegistrationTask().execute();
			
			}
		});

		
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	class RegistrationTask extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(PaymentActivity.this);
			pDialog.setMessage(Html.fromHtml("Please wait .."));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
			
			mCreditCardVo = new CreditCardVO(creditCard.getText().toString(), month.getText().toString(), year.getText().toString(), cvv.getText().toString(), zipCode.getText().toString());

		}

		protected String doInBackground(String... args) {
			// creating Places class object
			googlePlaces = new GooglePlaces();

			try {

				RestCall.getInstance().getRegistration(mRegisterVo,mCreditCardVo);
				String types = "cafe|restaurant"; // Listing places only cafes,
				double radius = 1000; // 1000 meters
				nearPlaces = googlePlaces.search(gps.getLatitude(),gps.getLongitude(), radius, types);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}


		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products


			Intent i = new Intent(getApplicationContext(),
					ShowMapActivity.class);
			// Sending user current geo location
			i.putExtra("user_latitude", Double.toString(gps.getLatitude()));
			i.putExtra("user_longitude", Double.toString(gps.getLongitude()));

			// passing near places to map activity
			i.putExtra("near_places", nearPlaces);
			// staring activity
			pDialog.dismiss();
			startActivity(i);
		}
	}

}