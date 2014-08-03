package com.pys.ui;

import java.util.Date;

import com.pys.hellogridview.R;
import com.pys.utility.ImageAdapter;
import com.pys.utility.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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

public class SearchActivity extends BaseActivity {
	private TabHost mTabHost;
	private String[] strArr = { "全部", "创意", "经典", "实用", "健康" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.SetActionBar(this, "搜索");
		// Utils.HideActionBar(this);
		setContentView(R.layout.activity_search);
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();

		for (int i = 0; i < strArr.length; i++) {
			setupTab(new TextView(this), strArr[i]);
		}

		GridView gridview = (GridView) findViewById(R.id.grvProducts);
		gridview.setAdapter(new ImageAdapter(this, this));

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
			}
		});

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
		String serverURL = "http://www.huajian-china.com/api/test/11";
		new GetApiData().execute(serverURL);
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

	private class GetApiData extends AsyncTask<String, Void, Void> {
		private String Content;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(SearchActivity.this);

		TextView txtOutput = (TextView) findViewById(R.id.txtOutput);
		TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);

		protected void onPreExecute() {
			Dialog.setMessage("Please wait..");
			Dialog.show();
		}

		// Call after onPreExecute method
		protected Void doInBackground(String... urls) {
			Content = Utils.GetStringFromUrl(urls[0]);
			return null;
		}

		protected void onPostExecute(Void unused) {
			txtOutput.setText(Content);
			Dialog.dismiss();
			jsonParsed.setText("Hello World!!");
		}
	}
}
