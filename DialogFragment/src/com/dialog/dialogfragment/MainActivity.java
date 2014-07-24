package com.dialog.dialogfragment;

import com.dialog.dialogfragment.ConfirmationDialogFragment.ConfirmationDialogFragmentListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity implements
		ConfirmationDialogFragmentListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void showDialog(View v) {
		ConfirmationDialogFragment confirmationDialog = ConfirmationDialogFragment
				.newInstance(R.string.dialog_format_title);
		confirmationDialog.setConfirmationDialogFragmentListener(this);
		confirmationDialog.show(getFragmentManager(), null);
	}

	@Override
	public void onPositiveClick() {
		Toast.makeText(this, android.R.string.ok, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onNegativeClick() {
		Toast.makeText(this, android.R.string.cancel, Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
