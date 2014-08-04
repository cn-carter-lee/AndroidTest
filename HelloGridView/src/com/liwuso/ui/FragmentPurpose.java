package com.liwuso.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liwuso.app.R;

public class FragmentPurpose extends BaseFragment {

	public FragmentPurpose() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.setNavgationTitle("ÇëÑ¡ÔñËÍÀñÄ¿µÄ");
		View rootView = inflater.inflate(R.layout.fragment_purpose, container,
				false);
		return rootView;
	}

}
