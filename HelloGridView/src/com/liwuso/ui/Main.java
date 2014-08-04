package com.liwuso.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import com.liwuso.app.R;

public class Main extends ActionBarActivity {

	private int[] unPressedIcons = { R.drawable.ic_so, R.drawable.ic_search,
			R.drawable.ic_favorite, R.drawable.ic_more };
	private int[] pressedIcons = { R.drawable.ic_so_pressed,
			R.drawable.ic_search_pressed, R.drawable.ic_favorite_pressed,
			R.drawable.ic_more_pressed };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		getSupportFragmentManager().beginTransaction()
				.add(R.id.container, new FragmentPerson()).commit();
	}

	public void clickBar(View view) {
		int viewId = view.getId();
		switch (viewId) {
		case R.id.bottom_btn0:
			clickFragment(new FragmentPerson(), 0);
			break;
		case R.id.bottom_btn1:
			clickFragment(new FragmentSearch(), 1);
			break;
		case R.id.bottom_btn2:
			clickFragment(new FavoriteFrament(), 2);
			break;
		case R.id.bottom_btn3:
			clickFragment(new FragmentMore(), 3);
			break;
		}
	}

	public void searchPerson(View view) {
		relaceFragment(new FragmentAge());
	}

	public void searchAge(View view) {
		relaceFragment(new FragmentPurpose());
	}

	public void searchPurpose(View view) {
		relaceFragment(new FragmentProduct());
	}

	public void clickMore(View view) {
		switch (view.getId()) {
		case R.id.btnAbout:
			relaceFragment(new FragmentMoreInfo(0));
			break;
		case R.id.btnQuestion:
			relaceFragment(new FragmentMoreInfo(1));
			break;
		case R.id.btnAgreement:
			relaceFragment(new FragmentMoreInfo(2));
			break;
		case R.id.btnContact:
			relaceFragment(new FragmentMoreInfo(3));
			break;
		case R.id.btnAdvice:
			relaceFragment(new FragmentAdvice());
			break;
		case R.id.btnCheckVertion:
			relaceFragment(new FragmentVersion());
			break;
		}
	}

	private void relaceFragment(Fragment newFragment) {
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.container, newFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	private void clickFragment(Fragment newFragment, int iconIndex) {
		for (int i = 0; i < 4; i++) {
			// if (i == iconIndex)
			// menu.getItem(i).setIcon(pressedIcons[i]);
			// else
			// menu.getItem(i).setIcon(unPressedIcons[i]);
		}
		relaceFragment(newFragment);
	}
}
