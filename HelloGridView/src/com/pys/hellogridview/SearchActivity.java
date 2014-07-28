package com.pys.hellogridview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class SearchActivity extends BaseActivity {
	private TabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.HideActionBar(this);
		setContentView(R.layout.activity_search);
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		addTab(mTabHost, "全部");
		addTab(mTabHost, "创意");
		addTab(mTabHost, "经典");
		addTab(mTabHost, "实用");
		addTab(mTabHost, "健康");

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

	private void addTab(TabHost tabHost, String tag) {
		TabSpec tab1 = tabHost.newTabSpec(tag);
		tab1.setIndicator(tag);
		tab1.setContent(R.id.onglet1);
		mTabHost.addTab(tab1);

		View tabview = createTabView(tabHost.getContext(),tag);
		TabSpec setContent = tabHost.newTabSpec(tag).setIndicator(tabview).set
	}

	private void setupTab(final View view, final String tag) {

		View tabview = createTabView(mTabHost.getContext(), tag);

		TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview)
				.setContent(new TabContentFactory() {
					public View createTabContent(String tag) {
						return view;
					}

				});

		mTabHost.addTab(setContent);

	}

	private static View createTabView(final Context context, final String text) {

		View view = LayoutInflater.from(context)
				.inflate(R.layout.tabs_bg, null);

		TextView tv = (TextView) view.findViewById(R.id.tabsText);

		tv.setText(text);

		return view;

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
