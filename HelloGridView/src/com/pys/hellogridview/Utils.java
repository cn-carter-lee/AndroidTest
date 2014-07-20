package com.pys.hellogridview;

import java.io.InputStream;
import java.io.OutputStream;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
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

	public static void SetActionBar(Activity activity, String title) {
		ActionBar actionBar = ((ActionBarActivity) activity)
				.getSupportActionBar();
		actionBar.setCustomView(R.layout.gift_actonbar);
		TextView textView = (TextView) activity
				.findViewById(R.id.gift_actionbar_title);
		textView.setText(title);
	}
}