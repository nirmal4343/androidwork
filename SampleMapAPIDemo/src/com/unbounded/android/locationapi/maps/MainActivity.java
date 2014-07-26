package com.unbounded.android.locationapi.maps;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {

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
	Button btnShowOnMap;

	// Progress dialog
	ProgressDialog pDialog;

	// Places Listview
	ListView lv;

	// ListItems data
	ArrayList<HashMap<String, String>> placesListItems = new ArrayList<HashMap<String, String>>();

	DrawerLayout mDrawerLayout;
	// KEY Strings
	public static String KEY_REFERENCE = "reference"; // id of the place
	public static String KEY_NAME = "name"; // name of the place
	public static String KEY_VICINITY = "vicinity"; // Place area name

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		isInternetPresent = cd.isConnectingToInternet();
		if (!isInternetPresent) {
			// Internet Connection is not present
			alert.showAlertDialog(MainActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}

		// creating GPS Class object
		gps = new GPSTracker(this);

		// check if GPS location can get
		if (gps.canGetLocation()) {
			Log.d("Your Location", "latitude:" + gps.getLatitude()
					+ ", longitude: " + gps.getLongitude());
		} else {
			// Can't get user's current location
			alert.showAlertDialog(MainActivity.this, "GPS Status",
					"Couldn't get location information. Please enable GPS",
					false);
			// stop executing code by return
			return;
		}

		// button show on map
		btnShowOnMap = (Button) findViewById(R.id.button1);

		/** Button click event for shown on map */
		btnShowOnMap.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				/*
				 * Intent i = new
				 * Intent(getApplicationContext(),ShowMapActivity.class); //
				 * Sending user current geo location i.putExtra("user_latitude",
				 * Double.toString(gps.getLatitude()));
				 * i.putExtra("user_longitude",
				 * Double.toString(gps.getLongitude()));
				 * 
				 * // passing near places to map activity
				 * i.putExtra("near_places", nearPlaces); // staring activity
				 * startActivity(i);
				 */
				new LoadPlaces().execute();
			}
		});

	}

	/**
	 * Background Async Task to Load Google places
	 * */
	class LoadPlaces extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage(Html.fromHtml("Loading Places.."));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting Places JSON
		 * */
		protected String doInBackground(String... args) {
			// creating Places class object
			googlePlaces = new GooglePlaces();

			try {
				// Separeate your place types by PIPE symbol "|"
				// If you want all types places make it as null
				// Check list of types supported by google
				String types = "cafe|restaurant"; // Listing places only cafes,
													// restaurants
				// Radius in meters - increase this value if you don't find any
				// places
				double radius = 1000; // 1000 meters
				// get nearest places
				nearPlaces = googlePlaces.search(gps.getLatitude(),
						gps.getLongitude(), radius, types);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog and show
		 * the data in UI Always use runOnUiThread(new Runnable()) to update UI
		 * from background thread, otherwise you will get error
		 * **/

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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item != null && item.getItemId() == android.R.id.home) {
	        if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
	            mDrawerLayout.closeDrawer(Gravity.RIGHT);
	        } else {
	            mDrawerLayout.openDrawer(Gravity.RIGHT);
	        }
	    }
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}