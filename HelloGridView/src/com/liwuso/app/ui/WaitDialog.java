package com.liwuso.app.ui;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.liwuso.app.R;

public class WaitDialog extends DialogFragment {

	private int layoutResource;

	public WaitDialog(int layoutResource) {
		this.layoutResource = layoutResource;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setStyle(DialogFragment.STYLE_NO_FRAME, R.style.MyDialog);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(layoutResource, container, false);
		return v;

	}
}