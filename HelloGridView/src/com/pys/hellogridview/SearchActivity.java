package com.pys.hellogridview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
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

		setupTab(new TextView(this), "全部");
		setupTab(new TextView(this), "创意");
		setupTab(new TextView(this), "经典");

		setupTab(new TextView(this), "实用");
		setupTab(new TextView(this), "健康");

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

	private View createTabView(final Context context, final String text) {

		View view = LayoutInflater.from(context).inflate(
				R.layout.search_tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;
	}

	public void searchProduct(View view) {

	}

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
