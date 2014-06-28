package com.unbounded.android.locationapi.maps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

@SuppressLint("NewApi")
public class ShowMapActivity extends Activity {

	// Google Map
	private GoogleMap googleMap;
	private GPSTracker gps;
	private PlacesList nearPlaces;
	String user_latitude;
	String user_longitude;
	private TextView addressView;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	CustomDrawerAdapter adapter;
	List<DrawerItem> dataList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_map_activity);
		
		
		gps = new GPSTracker(this);
		addressView = (TextView) findViewById(R.id.address);
		Intent i = getIntent();
		user_latitude = i.getStringExtra("user_latitude");
		user_longitude = i.getStringExtra("user_longitude");

		// Nearplaces list
		nearPlaces = (PlacesList) i.getSerializableExtra("near_places");

		dataList = new ArrayList<DrawerItem>();
		//mTitle = mDrawerTitle = getTitle();
		getActionBar().setTitle("");
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// Add Drawer Item to dataList
		// Add Drawer Item to dataList
		dataList.add(new DrawerItem("PROFILE", R.drawable.ic_contacts));
		dataList.add(new DrawerItem("PAYMENT", R.drawable.ic_card_dev));
		dataList.add(new DrawerItem("PROMOTIONS", R.drawable.ic_unwatched));
		dataList.add(new DrawerItem("SHARE", R.drawable.share));
		dataList.add(new DrawerItem("SUPPORT", R.drawable.ic_support));
		dataList.add(new DrawerItem("About", R.drawable.ca_cmas_info_read));
		dataList.add(new DrawerItem("HELP", R.drawable.ic_help));

		adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
				dataList);

		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		
		
		  getActionBar().setDisplayHomeAsUpEnabled(true);
		  getActionBar().setHomeButtonEnabled(true);
		   
		  mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
		              R.drawable.ic_drawer, R.string.drawer_open,
		              R.string.drawer_close) {
		        public void onDrawerClosed(View view) {
		            //  getActionBar().setTitle(mTitle);
		              invalidateOptionsMenu(); // creates call to
		                                                        // onPrepareOptionsMenu()
		        }
		   
		        public void onDrawerOpened(View drawerView) {
		         //     getActionBar().setTitle(mDrawerTitle);
		              invalidateOptionsMenu(); // creates call to
		                                                        // onPrepareOptionsMenu()
		        }
		  };
		   
		  mDrawerLayout.setDrawerListener(mDrawerToggle);
		   
		  if (savedInstanceState == null) {
		        SelectItem(0);
		  }
		
		
		try {
			// Loading map
			initilizeMap();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void SelectItem(int possition) {

		mDrawerList.setItemChecked(possition, true);
		//setTitle(dataList.get(possition).getItemName());
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	private void initilizeMap() {

		if (gps.canGetLocation()) {

			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();
			if (googleMap == null) {
				googleMap = ((MapFragment) getFragmentManager()
						.findFragmentById(R.id.map)).getMap();
				// latitude and longitude
				googleMap.clear();
			}

			MarkerOptions markerOptions = new MarkerOptions();
			LatLng latLng = new LatLng(latitude, longitude);
			// Setting the position for the marker
			markerOptions.position(latLng);
			markerOptions.icon(BitmapDescriptorFactory
					.fromResource(R.drawable.ic_pin));
			// Setting the title for the marker.
			// This will be displayed on taping the marker
			markerOptions.title("20 MIN to Pickups");

			Marker marker = googleMap.addMarker(markerOptions);

			marker.showInfoWindow();

			googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
			googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
			setGeoCoder(latitude, longitude);
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}

	private void setGeoCoder(double latitude, double longitude) {
		Geocoder geocoder = new Geocoder(this, Locale.getDefault());
		String addressText = "No Address Found";
		try {
			List<Address> addresses = geocoder.getFromLocation(latitude,
					longitude, 1);

			Address address = addresses.get(0);
			addressText = String.format(
					"%s, %s, %s",
					// If there's a street address, add it
					address.getMaxAddressLineIndex() > 0 ? address
							.getAddressLine(0) : "",
					// Locality is usually a city
					address.getLocality(),
					// The country of the address
					address.getCountryName());
			// Return the text
			// return addressText;
			addressView.setText(addressText);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
	//	getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return false;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			SelectItem(position);

		}
	}

}
