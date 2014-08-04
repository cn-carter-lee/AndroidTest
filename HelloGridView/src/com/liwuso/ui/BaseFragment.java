package com.liwuso.ui;

import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {

	protected void setNavgationTitle(String title) {

		if (this.getActivity() instanceof Main) {
			Main main = (Main) this.getActivity();
			main.setTitle(title);
		}
	}

}
