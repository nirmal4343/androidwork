package com.local.courier.pkg.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.local.courier.model.RegisterVO;
import com.unbounded.android.locationapi.maps.R;

public class RegisterActivity extends Activity {

	public static final String OBJECT_KEY = "REGISTER";
	private Button btnNext;
	
	private EditText fName;
	private EditText lName;
	private EditText email;
	private EditText password;
	private EditText phone;

	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regester_main);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		// button show on map
		btnNext = (Button) findViewById(R.id.next);
		
		fName = (EditText) findViewById(R.id.fName);
		lName = (EditText) findViewById(R.id.lName);
		email = (EditText) findViewById(R.id.email);
		phone = (EditText) findViewById(R.id.phone_no);
		password = (EditText) findViewById(R.id.password);
		

		/** Button click event for shown on map */
		
		btnNext.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent paymentInt = new Intent(RegisterActivity.this, PaymentActivity.class);
				RegisterVO registerVo = new RegisterVO(fName.getText().toString(), lName.getText().toString(), email.getText().toString(), phone.getText().toString(), password.getText().toString());
				

		        Bundle mBundle = new Bundle();  
		        mBundle.putParcelable(OBJECT_KEY, registerVo);  
		        paymentInt.putExtras(mBundle);  

				startActivity(paymentInt);

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

}