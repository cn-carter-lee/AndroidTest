package com.liwuso.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.liwuso.utility.ImageAdapter;
import com.liwuso.app.R;

public class FragmentSearch extends BaseFragment {
	private TabHost mTabHost;
	private String[] strArr = { "全部", "创意", "经典", "实用", "健康" };

	public FragmentSearch() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_search, container,
				false);

		mTabHost = (TabHost) rootView.findViewById(android.R.id.tabhost);
		mTabHost.setup();

		for (int i = 0; i < strArr.length; i++) {
			setupTab(new TextView(this.getActivity()), strArr[i]);
		}

		GridView gridview = (GridView) rootView.findViewById(R.id.grvProducts);
		gridview.setAdapter(new ImageAdapter(this.getActivity(), this
				.getActivity()));

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

			}
		});

		return rootView;
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

}
