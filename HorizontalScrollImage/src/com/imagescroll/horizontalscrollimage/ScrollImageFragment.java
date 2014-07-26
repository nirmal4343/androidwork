package com.imagescroll.horizontalscrollimage;

import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ScrollImageFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	// Data for testing
	public String selectedIndex = "0";
	public HashMap<String, Object> map;

	public ScrollImageFragment() {
	}

	public static ScrollImageFragment newInstance(HashMap<String, Object> map) {

		ScrollImageFragment f = new ScrollImageFragment();
		f.map = map;
		return f;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main_image,
				container, false);
		ImageView cardImage = (ImageView) rootView
				.findViewById(R.id.imageView1);
		selectedIndex = Integer.toString(getArguments().getInt(
				ARG_SECTION_NUMBER));

		if (savedInstanceState != null) {
			this.map = (HashMap<String, Object>) savedInstanceState
					.getSerializable("oldMp");
			cardImage.setImageResource(Integer.parseInt(map.get(
					savedInstanceState.getString("current")).toString()));
		} else {
			cardImage.setImageResource(Integer.parseInt(map.get(selectedIndex)
					.toString()));
		}
		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Storing current state before configuration changes effects.

		if (this.map != null) {
			outState.putString("current", selectedIndex);
			outState.putSerializable("oldMp", this.map);
		}
		super.onSaveInstanceState(outState);
	}

}
