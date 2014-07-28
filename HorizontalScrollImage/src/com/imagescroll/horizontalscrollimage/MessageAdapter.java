package com.imagescroll.horizontalscrollimage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.message.poc.domain.Message;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter {

	private Activity activity;
	private List<Message> data;
	private static LayoutInflater inflater = null;

	public MessageAdapter(Activity a, List<Message> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.single_message_row, null);
		Message msg = data.get(position);
		TextView title = (TextView) vi.findViewById(R.id.senderId); // title
		TextView subject = (TextView) vi.findViewById(R.id.message_subject); // artist
		title.setText(msg.getSenderId());
		subject.setText(msg.getSubject());
		return vi;
	}

}
