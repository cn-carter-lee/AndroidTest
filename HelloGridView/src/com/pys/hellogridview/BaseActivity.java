package com.pys.hellogridview;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends ActionBarActivity {
	private int[] pressedIcons = { R.drawable.ic_action_liwusou,
			R.drawable.ic_action_liwusou, R.drawable.ic_action_liwusou,
			R.drawable.ic_action_liwusou };

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.base, menu);
		
		// menu.getItem(2).setIcon(R.drawable.ic_action_liwusou);
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
			intent = new Intent(activity, SoActivity.class);
			break;
		}
		if (intent != null) {
			intent.putExtra("", message);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
