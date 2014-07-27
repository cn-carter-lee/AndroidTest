package com.pys.hellogridview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class SearchActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.HideActionBar(this);
		setContentView(R.layout.activity_search);
		TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
		addTab(tabHost, "全部");
		addTab(tabHost, "创意");
		addTab(tabHost, "经典");
		addTab(tabHost, "实用");
		addTab(tabHost, "健康");

		GridView gridview = (GridView) findViewById(R.id.grvProducts);
		gridview.setAdapter(new ImageAdapter(this, this));

		// gridview.setOnItemClickListener(new OnItemClickListener() {
		// public void onItemClick(AdapterView<?> parent, View v, int
		// position, long id) {
		// Toast.makeText(HelloGridView.this, "" + position,
		// Toast.LENGTH_SHORT).show();
		// }
		// });

	}

	private void addTab(TabHost tabHost, String tabName) {
		TabSpec tab1 = tabHost.newTabSpec(tabName);
		tab1.setIndicator(tabName);
		tab1.setContent(R.id.onglet1);
		tabHost.addTab(tab1);

	}

	public void searchProduct(View view) {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_search,
					container, false);
			return rootView;
		}
	}

}
