package com.liwuso.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.liwuso.app.R;
import com.liwuso.app.widget.ScrollLayout;

public class More extends Activity {
	private ScrollLayout scrollLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more);

		scrollLayout = (ScrollLayout) findViewById(R.id.main_scrolllayout_more);

		Intent intent = getIntent();

		int item_index = intent.getIntExtra("itemIndex", 0);
		if (item_index < 4) {
			TextView myTextView = (TextView) findViewById(R.id.txtMoreInfo);
			myTextView.setText(Html.fromHtml(getResources().getStringArray(
					R.array.more_info)[item_index]));
			scrollLayout.scrollToScreen(0);
		} else if (item_index == 4) {
			scrollLayout.scrollToScreen(1);
		}
	}

}
