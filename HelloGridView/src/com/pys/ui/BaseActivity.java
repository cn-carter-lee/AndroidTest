package com.pys.ui;

import com.pys.hellogridview.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends ActionBarActivity {

	private int[] unPressedIcons = { R.drawable.ic_so, R.drawable.ic_search,
			R.drawable.ic_favorite, R.drawable.ic_more };
	private int[] pressedIcons = { R.drawable.ic_so_pressed,
			R.drawable.ic_search_pressed, R.drawable.ic_favorite_pressed,
			R.drawable.ic_more_pressed };

	private Menu menu;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.menu = menu;
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
		int id = item.getItemId();
		switch (id) {
		case R.id.action_gift_home:
			clickFragment(new SoAllFragment(), 0);
			break;
		case R.id.action_gift_search:
			clickFragment(new SearchFrament(), 1);
			break;
		case R.id.action_gift_favorite:
			clickFragment(new FavoriteFrament(), 2);
			break;
		case R.id.action_gift_more:
			clickFragment(new MoreFrament(), 3);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void clickFragment(Fragment newFragment, int iconIndex) {
		for (int i = 0; i < 4; i++) {
			if (i == iconIndex)
				menu.getItem(i).setIcon(pressedIcons[i]);
			else
				menu.getItem(i).setIcon(unPressedIcons[i]);
		}
		ActionBar actionBar = getSupportActionBar();
		// actionBar.hide();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(android.R.id.content, newFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}
}
