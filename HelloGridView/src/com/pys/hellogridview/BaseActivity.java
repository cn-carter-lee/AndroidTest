package com.pys.hellogridview;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends ActionBarActivity {
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.base, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		final BaseActivity activity = this;
		Intent intent = null;
		String message = "";

		switch (id) {
		case R.id.action_gift_more:
			intent = new Intent(activity, MoreActivity.class);
			break;
		case R.id.action_gift_search:
			intent = new Intent(activity, SearchActivity.class);
			break;
		}
		if (intent != null) {
			intent.putExtra("", message);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
