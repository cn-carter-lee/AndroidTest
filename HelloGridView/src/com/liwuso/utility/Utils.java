package com.liwuso.utility;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.liwuso.app.AppManager;
import com.liwuso.app.R;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

public class Utils {

	public static Context context = null;

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

	// Favorite

	public static void addFavorite(int id) {

	}

	public static String[] getFavoriteArray() {
		String[] product_id_array = readFavoriteFile().split(",");
		return product_id_array;
	}

	public static void writeFavoriteFile(Context context) {
		String filename = "liwuso_data";
		String string = "887,718,109,571";
		FileOutputStream outputStream;
		try {
			outputStream = context.openFileOutput(filename,
					Context.MODE_PRIVATE);
			outputStream.write(string.getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readFavoriteFile() {
		String result = "";
		String filename = "liwuso_data";
		try {
			FileInputStream fin = context.openFileInput(filename);
			int c;

			while ((c = fin.read()) != -1) {
				result = result + Character.toString((char) c);
			}
			// Toast.makeText(getBaseContext(), "file read:" + temp,
			// Toast.LENGTH_SHORT).show();
		} catch (Exception e) {

		}
		return result;
	}

	public static int getFavoriteCount() {
		int result = 0;
		try {
			result = getFavoriteArray().length;
		} catch (Exception e) {

		}
		return result;
	}
}