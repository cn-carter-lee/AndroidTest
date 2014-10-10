package com.liwuso.app.widget;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import android.widget.ScrollView;

public class PysScrollView extends ScrollView {

	public PysScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void requestChildFocus(View child, View focused) {
		if (focused instanceof WebView)
			return;
		super.requestChildFocus(child, focused);
	}
}
