package com.imagescroll.horizontalscrollimage;

import java.util.ArrayList;
import java.util.List;

import com.message.poc.domain.Message;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MessageInboxActivity extends Activity {

	ListView list;
	MessageAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_activity);

		list = (ListView) findViewById(R.id.list);

		List<Message> messageList = createDemoMessage();

		adapter = new MessageAdapter(this, messageList);

		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});
	}

	private String MESSAGE_TEMPLET = "Virginia State law requires that we make every attempt to collect past due amounts owed to the university. If your payment in full is not received in our office by the due date below, your account will be placed with a collection agency and you will be responsible for any collection cost incurred at a rate of one-third of the total due.If your account is returned unpaid by the collection agency, the following actions may then be taken: Credit Reporting: Your account will be listed by a credit bureau as a bad debt."
			+ "\nDebt Set Off: If you are a Virginia resident/business, your delinquent account can be collected in full from income tax refunds due you from the state."
			+ "\nAttorney General: Your account may be turned over to the Virginia Attorney General's Office for litigation."
			+ "\n\nHelp us to avoid this action by sending us your payment today. If you have questions regarding this procedure, please contact this office immediately.";

	private List<Message> createDemoMessage() {
		int count = 0;
		List<Message> msgList = new ArrayList<Message>();
		while (count < 7) {
			if (count == 7 || count == 6) {
				msgList.add(new Message("1", "info@incomm.com", true,
						"Payment Status", MESSAGE_TEMPLET));

			}else{
				msgList.add(new Message("1", "info@incomm.com", false,"Payment Status", MESSAGE_TEMPLET));
			}
			count++;
		}

		return msgList;
	}
}
