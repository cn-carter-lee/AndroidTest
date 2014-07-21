package com.pys.hellogridview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ProductActivity extends BaseActivity {
	ListView listview;
	LazyAdapter adapter;

	private ShareActionProvider mShareActionProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product);

		ActionBar actionBar = getSupportActionBar();
		Utils.SetActionBar(this, getString(R.string.product_actionbar_title));
		// actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// ActionBar actionBar = getSupportActionBar();
		// actionBar.setDisplayHomeAsUpEnabled(true);

		// actionBar.hide();
		// actionBar.setDisplayShowHomeEnabled(false);
		// actionBar.setDisplayShowTitleEnabled(false);
		/*
		 * if (savedInstanceState == null) {
		 * getSupportFragmentManager().beginTransaction() .add(R.id.container,
		 * new PlaceholderFragment()).commit(); }
		 */
		
		listview = (ListView) findViewById(R.id.listView1);
		adapter = new LazyAdapter(this, imageUrls);
		listview.setAdapter(adapter);
		// listview.setAdapter(new ImageAdapter(this));
		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "" + position,
						Toast.LENGTH_SHORT).show();
			}

		});
	}

	private Intent getDefaultIntent() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		return intent;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		// Handle presses on the action bar items
		switch (item.getItemId()) {
		/*
		 * case R.id.action_settings:
		 * 
		 * return true;
		 */
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onDestroy() {
		listview.setAdapter(null);
		super.onDestroy();
	}

	private String imageUrls[] = {
			"http://www.liwuso.com/Uploads/cpc/86.jpg",
			"http://www.liwuso.com/Uploads/cpc/89.jpg",
			"http://www.liwuso.com/Uploads/cpc/90.jpg",
			"http://www.liwuso.com/Uploads/cpc/91.jpg",
			"http://www.liwuso.com/Uploads/cpc/92.jpg",
			"http://www.liwuso.com/Uploads/cpc/93.jpg",
			"http://images.sports.cn/Image/2014/06/29/0832351428.jpg",
			"http://g.hiphotos.baidu.com/image/pic/item/42166d224f4a20a4e0b49b3e92529822730ed0a5.jpg" };

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_product,
					container, false);
			return rootView;
		}
	}

}
