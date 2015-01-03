package com.liwuso.utility;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.content.Context;
import android.util.Log;

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
	public static String FAVORITE_FILENAME = "liwuso_data";

	public static void addFavorite(int id) {
		List<String> list = getFavoriteList();
		list.add(String.valueOf(id));
		saveFavorite(list);
	}

	public static void deleteFavorite(int id) {
		List<String> list = getFavoriteList();
		list.remove(String.valueOf(id));
		saveFavorite(list);
	}

	public static void saveFavorite(List<String> list) {
		String fileContent = "";
		for (String id : list) {
			if (fileContent == "")
				fileContent += String.valueOf(id);
			else
				fileContent += ("," + String.valueOf(id));
		}
		writeFavoriteFile(fileContent);
	}

	public static String[] getFavoriteArray() {
		return readFavoriteFile().split(",");
	}

	public static boolean exists(int id) {
		return getFavoriteList().contains(String.valueOf(id));

	}

	public static List<String> getFavoriteList() {
		String content = readFavoriteFile();
		String[] arr;
		if (content == "")
			arr = new String[0];
		else
			arr = content.split(",");
		List<String> list = new ArrayList<String>(Arrays.asList(arr));
		return list;
	}

	public static void writeFavoriteFile(String content) {
		FileOutputStream outputStream;
		try {
			outputStream = context.openFileOutput(FAVORITE_FILENAME,
					Context.MODE_PRIVATE);
			outputStream.write(content.getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readFavoriteFile() {
		String result = "";
		try {
			String[] fileList = context.fileList();
			if (Arrays.asList(fileList).contains(FAVORITE_FILENAME)) {
				FileInputStream fin = context.openFileInput(FAVORITE_FILENAME);
				int c;
				while ((c = fin.read()) != -1) {
					result = result + Character.toString((char) c);
				}

			} else {
				result = "";
				context.openFileOutput(FAVORITE_FILENAME, Context.MODE_PRIVATE)
						.write(result.getBytes());
			}

		} catch (Exception e) {

		}
		return result;
	}

	public static int getFavoriteCount() {
		int result = 0;
		try {
			result = getFavoriteList().size();
		} catch (Exception e) {

		}
		return result;
	}

}