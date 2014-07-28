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
import android.widget.ImageView;
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
		ViewHolderItem viewHolder;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.single_message_row, null);

			viewHolder = new ViewHolderItem();

			viewHolder.title = (TextView) convertView.findViewById(R.id.senderId); // title
			viewHolder.subject = (TextView) convertView
					.findViewById(R.id.message_subject); // artist
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.message_icon); // artist
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolderItem) convertView.getTag();

		}

		Message msg = data.get(position);

		viewHolder.title.setText(msg.getSenderId());
		viewHolder.subject.setText(msg.getSubject());

		if (msg.getReadStatus() == true) {
			viewHolder.imageView.setImageResource(R.drawable.message_read);
		}

		return convertView;
	}

	static class ViewHolderItem {

		TextView title;
		TextView subject;
		ImageView imageView;

	}

}
