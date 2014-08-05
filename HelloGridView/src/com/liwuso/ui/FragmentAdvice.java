package com.liwuso.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liwuso.app.R;

public class FragmentAdvice extends BaseFragment {

	public FragmentAdvice() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.setNavgationTitle("意见建议");
		View rootView = inflater.inflate(R.layout.fragment_advice, container,
				false);
		return rootView;
	}
}
