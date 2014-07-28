package com.pys.hellogridview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends ActionBarActivity {

	public final static String EXTRA_MESSAGE = "com.pys.hellogridview.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		final MainActivity activity = this;
		new android.os.Handler().postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent(activity, FavoriteActivity.class);
				String message = "";
				intent.putExtra(EXTRA_MESSAGE, message);
				startActivity(intent);
			}
		}, 200);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

}
