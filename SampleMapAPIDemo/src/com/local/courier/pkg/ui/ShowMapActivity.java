package com.local.courier.pkg.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.unbounded.android.locationapi.maps.CustomDrawerAdapter;
import com.unbounded.android.locationapi.maps.DrawerItem;
import com.unbounded.android.locationapi.maps.GPSTracker;
import com.unbounded.android.locationapi.maps.PlacesList;
import com.unbounded.android.locationapi.maps.R;
import com.unbounded.android.locationapi.maps.R.drawable;
import com.unbounded.android.locationapi.maps.R.id;
import com.unbounded.android.locationapi.maps.R.layout;

@SuppressLint("NewApi")
public class ShowMapActivity extends Activity {

	// Google Map Sample
	private GoogleMap googleMap;
	private GPSTracker gps;
	private PlacesList nearPlaces;
	String user_latitude;
	String user_longitude;
	private TextView addressView;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;


	CustomDrawerAdapter adapter;
	List<DrawerItem> dataList;

	private boolean drawerOpen = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_map_activity);

		// Adding custom Header

		ActionBar mActionBar = getActionBar();
		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setDisplayShowTitleEnabled(false);
		LayoutInflater mInflater = LayoutInflater.from(this);

		View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
		TextView mTitleTextView = (TextView) mCustomView
				.findViewById(R.id.title_text);
		mTitleTextView.setText("Your BRAND");

		ImageButton imageButton = (ImageButton) mCustomView
				.findViewById(R.id.imageButton);
		imageButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				if (!drawerOpen) {
					mDrawerLayout.openDrawer(Gravity.END);
					drawerOpen = true;
				} else {
					mDrawerLayout.closeDrawer(Gravity.END);
					drawerOpen = false;
				}
			}
		});

		mActionBar.setCustomView(mCustomView);
		mActionBar.setDisplayShowCustomEnabled(true);

		// Custom Hear addition complete

		gps = new GPSTracker(this);
		addressView = (TextView) findViewById(R.id.address);
		Intent i = getIntent();
		user_latitude = i.getStringExtra("user_latitude");
		user_longitude = i.getStringExtra("user_longitude");

		// Nearplaces list
		nearPlaces = (PlacesList) i.getSerializableExtra("near_places");

		dataList = new ArrayList<DrawerItem>();
		// mTitle = mDrawerTitle = getTitle();
		getActionBar().setTitle("");
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.right_drawer);

		// mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,GravityCompat.START);

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

			// Setting a custom info window adapter for the google map
			googleMap.setInfoWindowAdapter(new InfoWindowAdapter() {

				// Use default InfoWindow frame
				@Override
				public View getInfoWindow(Marker arg0) {
					return null;
				}

				// Defines the contents of the InfoWindow
				@Override
				public View getInfoContents(Marker arg0) {

					// Getting view from the layout file info_window_layout
					View v = getLayoutInflater().inflate(
							R.layout.info_window_layout, null);

					// Getting the position from the marker
					LatLng latLng = arg0.getPosition();

					// Getting reference to the TextView to set longitude
					TextView tvLng = (TextView) v
							.findViewById(R.id.distance_info);

					// Returning the view containing InfoWindow contents
					return v;

				}
			});

			// Defining Listeners to the info window
			googleMap
					.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

						@Override
						public void onInfoWindowClick(Marker arg0) {
							// TODO Auto-generated method stub
							Toast.makeText(ShowMapActivity.this,
									"You Clicked...", Toast.LENGTH_LONG).show();// display
																				// toast

						}

					});

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


	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			SelectItem(position);
			drawerOpen = false;

		}
	}

}
