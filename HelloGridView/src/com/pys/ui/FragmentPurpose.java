package com.pys.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pys.hellogridview.R;

public class FragmentPurpose extends Fragment {

	public FragmentPurpose() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_purpose, container,
				false);
		return rootView;
	}

}
