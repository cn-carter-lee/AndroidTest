package com.pys.hellogridview;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends ActionBarActivity {
	private int[] pressedIcons = { R.drawable.ic_so_pressed,
			R.drawable.ic_search_pressed, R.drawable.ic_favorite_pressed,
			R.drawable.ic_more_pressed };

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.base, menu);
		int iconIndex = 0;
		if (this instanceof SearchActivity) {
			iconIndex = 1;
		} else if (this instanceof FavoriteActivity) {
			iconIndex = 2;
		} else if (this instanceof MoreActivity) {
			iconIndex = 3;
		}
		menu.getItem(iconIndex).setIcon(pressedIcons[iconIndex]);
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
		case R.id.action_gift_home:
			intent = new Intent(activity, SoActivity.class);
			break;
		case R.id.action_gift_search:
			intent = new Intent(activity, SearchActivity.class);
			break;
		case R.id.action_gift_favorite:
			intent = new Intent(activity, FavoriteActivity.class);
			break;
		case R.id.action_gift_more:
			intent = new Intent(activity, MoreActivity.class);
			break;
		}
		if (intent != null) {
			intent.putExtra("", message);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
