package com.imagescroll.horizontalscrollimage;

import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */


public class HorizontalScrollImageAdapter extends FragmentPagerAdapter {

	static HashMap<String, Object> map = new HashMap<String, Object>();
	static {
		map.put("0", String.valueOf(R.drawable.capture_2));
		map.put("1", String.valueOf(R.drawable.capture_3));
		map.put("2", String.valueOf(R.drawable.capture_4));
	}

	public HorizontalScrollImageAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		// getItem is called to instantiate the fragment for the given page.
		// Return a DummySectionFragment (defined as a static inner class
		// below) with the page number as its lone argument.
		Fragment fragment = ScrollImageFragment.newInstance(map);
		Bundle args = new Bundle();
		args.putInt(ScrollImageFragment.ARG_SECTION_NUMBER, position);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getCount() {
		// Show 3 total pages.
		return map.size();
	}

}