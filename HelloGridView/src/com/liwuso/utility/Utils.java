package com.liwuso.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.liwuso.app.R;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

public class Utils {
	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

//	public static void SetActionBar(Activity activity, String title) {
//		ActionBar actionBar = ((ActionBarActivity) activity)
//				.getSupportActionBar();
//		actionBar.setCustomView(R.layout.gift_actonbar);
//		TextView textView = (TextView) activity
//				.findViewById(R.id.gift_actionbar_title);
//		textView.setText(title);
//	}
//
//	public static void HideActionBar(Activity activity) {
//		ActionBar actionBar = ((ActionBarActivity) activity)
//				.getSupportActionBar();
//		actionBar.setCustomView(R.layout.gift_actonbar);
//		actionBar.hide();
//	}

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static String GetStringFromUrl(String url) {
		HttpClient httpclient = new DefaultHttpClient();
		String Content = null;
		HttpGet httpGet = new HttpGet(url);
		// Execute the request
		HttpResponse response;
		try {
			response = httpclient.execute(httpGet);
			// Examine the response status
			Log.i("Praeda", response.getStatusLine().toString());
			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			// If the response does not enclose an entity, there is no need
			// to worry about connection release
			if (entity != null) {
				InputStream instream = entity.getContent();
				Content = Utils.convertStreamToString(instream);
				instream.close();
			}

		} catch (Exception e) {
		}
		return Content;
	}	
}