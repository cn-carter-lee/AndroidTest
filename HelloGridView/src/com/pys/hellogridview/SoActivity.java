package com.pys.hellogridview;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SoActivity extends BaseActivity implements
		SoAllFragment.OnFragmentInteractionListener,
		SoMaleFragment.OnFragmentInteractionListener,
		SoFemaleFragment.OnFragmentInteractionListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		Utils.SetActionBar(this, getString(R.string.gift_actionbar_liwusou));
		Tab tab = actionBar
				.newTab()
				.setText(R.string.gift_all)
				.setTabListener(
						new SoTabListener<SoAllFragment>(this, "album",
								SoAllFragment.class));
		actionBar.addTab(tab);

		tab = actionBar
				.newTab()
				.setText(R.string.gift_female)
				.setTabListener(
						new SoTabListener<SoFemaleFragment>(this,
								"album", SoFemaleFragment.class));
		actionBar.addTab(tab);

		tab = actionBar
				.newTab()
				.setText(R.string.gift_male)
				.setTabListener(
						new SoTabListener<SoMaleFragment>(this, "album",
								SoMaleFragment.class));
		actionBar.addTab(tab);
	}

	@Override
	public void onFragmentInteraction(Uri uri) {
		// TODO Auto-generated method stub

	}

	public void searchPerson(View view) {
		ActionBar actionBar = getSupportActionBar();
		// actionBar.hide();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		SoAgeFrament newFragment = new SoAgeFrament();

		// transaction.add(0, newFragment);
		transaction.replace(android.R.id.content, newFragment);
		transaction.addToBackStack(null);
		// Commit the transaction
		transaction.commit();
	}

	public void searchAge(View view) {
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		SoPurposeFrament newFragment = new SoPurposeFrament();

		transaction.replace(android.R.id.content, newFragment);
		transaction.addToBackStack(null);
		// Commit the transaction
		transaction.commit();
	}

	public void searchPurpose(View view) {
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		SoProductFrament newFragment = new SoProductFrament();
		transaction.replace(android.R.id.content, newFragment);
		transaction.addToBackStack(null);
		// Commit the transaction
		transaction.commit();
	}

	public static class SoTabListener<T extends Fragment> implements
			ActionBar.TabListener {
		private Fragment mFragment;
		private final Activity mActivity;
		private final String mTag;
		private final Class<T> mClass;

		public SoTabListener(Activity activity, String tag, Class<T> clz) {
			this.mActivity = activity;
			this.mTag = tag;
			this.mClass = clz;
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			if (mFragment == null) {
				mFragment = Fragment.instantiate(mActivity, mClass.getName());
				ft.add(android.R.id.content, mFragment, mTag);
			} else {
				ft.attach(mFragment);
			}
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			if (mFragment != null) {
				// Detach the fragment, because another one is being attached
				ft.detach(mFragment);
			}
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// ((ActionBarActivity) this.getActivity()).getSupportActionBar()
			// .setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		}
	}

	public static class SoAgeFrament extends Fragment {

		public SoAgeFrament() {

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Utils.SetActionBar(this.getActivity(),
					getString(R.string.age_actionbar_title));
			View rootView = inflater.inflate(R.layout.fragment_so_age,
					container, false);
			return rootView;
		}
	}

	public static class SoPurposeFrament extends Fragment {

		public SoPurposeFrament() {

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Utils.SetActionBar(this.getActivity(),
					getString(R.string.purpose_actionbar_title));
			View rootView = inflater.inflate(R.layout.fragment_so_purpose,
					container, false);
			return rootView;
		}
	}

	public static class SoProductFrament extends Fragment {
		ListView listview;
		LazyAdapter adapter;

		private ShareActionProvider mShareActionProvider;

		public SoProductFrament() {

		}

		private String imageUrls[] = {
				"http://www.liwuso.com/Uploads/cpc/86.jpg",
				"http://www.liwuso.com/Uploads/cpc/89.jpg",
				"http://www.liwuso.com/Uploads/cpc/90.jpg",
				"http://www.liwuso.com/Uploads/cpc/91.jpg",
				"http://www.liwuso.com/Uploads/cpc/92.jpg",
				"http://www.liwuso.com/Uploads/cpc/93.jpg",
				"http://images.sports.cn/Image/2014/06/29/0832351428.jpg",
				"http://g.hiphotos.baidu.com/image/pic/item/42166d224f4a20a4e0b49b3e92529822730ed0a5.jpg" };

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Utils.SetActionBar(this.getActivity(),
					getString(R.string.product_actionbar_title));
			View rootView = inflater.inflate(R.layout.fragment_so_product,
					container, false);
			super.onCreate(savedInstanceState);
			// Populate images info
			final Activity activity = this.getActivity();
			listview = (ListView) rootView.findViewById(R.id.listViewProduct);
			adapter = new LazyAdapter(activity, imageUrls);
			listview.setAdapter(adapter);
			return rootView;
		}
	}
}
