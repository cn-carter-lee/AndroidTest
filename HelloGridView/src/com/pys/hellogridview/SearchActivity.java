package com.pys.hellogridview;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

		// gridview.setOnItemClickListener(new OnItemClickListener() {
		// public void onItemClick(AdapterView<?> parent, View v, int
		// position, long id) {
		// Toast.makeText(HelloGridView.this, "" + position,
		// Toast.LENGTH_SHORT).show();
		// }
		// });

		String serverURL = "http://www.huajian-china.com/api/test/11";
		//String serverURL = "http://www.baidu.com";
		new LongOperation().execute(serverURL);
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

	private class LongOperation extends AsyncTask<String, Void, Void> {

		private final HttpClient Client = new DefaultHttpClient();
		private String Content;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(SearchActivity.this);
		String data = "";
		TextView uiUpdate = (TextView) findViewById(R.id.output);
		TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);
		int sizeData = 0;
		EditText serverText = (EditText) findViewById(R.id.serverText);

		protected void onPreExecute() {
			// NOTE: You can call UI Element here.Start Progress Dialog
			// (Message)
			Dialog.setMessage("Please wait..");
			Dialog.show();
			try {
				// Set Request parameter
				data += "&" + URLEncoder.encode("data", "UTF-8") + "="
						+ serverText.getText();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		// Call after onPreExecute method
		protected Void doInBackground(String... urls) {
			/************ Make Post Call To Web Server ***********/
			BufferedReader reader = null;
			// Send data
			try {
				// Defined URL where to send data
				URL url = new URL(urls[0]);
				// Send POST data request
				URLConnection conn = url.openConnection();
				conn.setConnectTimeout(5000);
				conn.setDoOutput(true);
				OutputStreamWriter wr = new OutputStreamWriter(
						conn.getOutputStream());
				//wr.write(data);
				//wr.flush();
				// Get the server response
				reader = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = null;

				// Read Server Response
				while ((line = reader.readLine()) != null) {
					// Append server response in string
					sb.append(line + "");
				}
				// Append Server Response To Content String
				Content = sb.toString();
			} catch (Exception ex) {
				Error = ex.getMessage();
			} finally {
				try {
					reader.close();
				} catch (Exception ex) {
				}
			}
			return null;
		}

		protected void onPostExecute(Void unused) {
			// NOTE: You can call UI Element here.Close progress dialog
			Dialog.dismiss();
			if (Error != null) {
				uiUpdate.setText("Output : " + Error);

			} else {
				// Show Response Json On Screen (activity)
				uiUpdate.setText(Content);
				/****************** Start Parse Response JSON Data *************/
				String OutputData = "";
				JSONObject jsonResponse;
				try {
					/******
					 * Creates a new JSONObject with name/value mappings from
					 * the JSON string.
					 ********/
					jsonResponse = new JSONObject(Content);
					/******
					 * Returns the value mapped by name if it exists and is a
					 * JSONArray.********** Returns null otherwise.
					 *******/
					JSONArray jsonMainNode = jsonResponse.toJSONArray(null);
							//.optJSONArray("Android");
					/*********** Process each JSON Node ************/
					int lengthJsonArr = jsonMainNode.length();
					for (int i = 0; i < lengthJsonArr; i++) {
						/****** Get Object for each JSON node. ***********/
						JSONObject jsonChildNode = jsonMainNode
								.getJSONObject(i);
						/******* Fetch node values **********/
						String name = jsonChildNode.optString("Name")
								.toString();
						String number = jsonChildNode.optString("Id")
								.toString();
						String date_added = jsonChildNode.optString(
								"Price").toString();

						OutputData += " Name           : "
								+ name
								+ " "
								+ "Id      : "
								+ number
								+ "  "
								+ "Price                : "
								+ date_added
								+ "  "
								+ "--------------------------------------------------";
					}
					/****************** End Parse Response JSON Data *************/
					// Show Parsed Output on screen (activity)
					jsonParsed.setText(OutputData);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
