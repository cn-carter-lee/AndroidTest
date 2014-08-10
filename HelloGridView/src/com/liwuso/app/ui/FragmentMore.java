package com.liwuso.app.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liwuso.app.R;

public class FragmentMore extends BaseFragment {

	public FragmentMore() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.setNavgationTitle("¸ü¶à");
		View rootView = inflater.inflate(R.layout.fragment_more, container,
				false);
		return rootView;
	}

}
