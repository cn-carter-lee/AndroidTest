package com.liwuso.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.liwuso.app.R;

public class FragmentPerson extends BaseFragment {
	private TabHost mTabHost;
	private String[] tagTitles = { "全部", "女性", "男性" };
	private int[] tabSources = { R.layout.tab_all, R.layout.tab_female,
			R.layout.tab_male };

	public FragmentPerson() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_person, container,
				false);
		mTabHost = (TabHost) rootView.findViewById(android.R.id.tabhost);

		mTabHost.setup();
		for (int i = 0; i < tagTitles.length; i++) {
			setupTab(new TextView(this.getActivity()), i);
		}
		this.setNavgationTitle("您要送谁礼物?");
		return rootView;
	}

	private void setupTab(final View view, final int i) {
		View tabview = createTabView(mTabHost.getContext(), tagTitles[i]);
		TabSpec setContent = mTabHost.newTabSpec(tagTitles[i])
				.setIndicator(tabview).setContent(new TabContentFactory() {
					public View createTabContent(String tag) {
						return LayoutInflater.from(mTabHost.getContext())
								.inflate(tabSources[i], null);
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