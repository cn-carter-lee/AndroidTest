package com.liwuso.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liwuso.app.R;

public class FragmentAge extends BaseFragment {

	public FragmentAge() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.setNavgationTitle("��ѡ��Ů���ѵ�����");
		View rootView = inflater.inflate(R.layout.fragment_age, container,
				false);

		return rootView;
	}

}
