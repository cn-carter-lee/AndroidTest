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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class SearchActivity extends BaseActivity implements
		SearchAllFragment.OnFragmentInteractionListener,
		SearchMaleFragment.OnFragmentInteractionListener,
		SearchFemaleFragment.OnFragmentInteractionListener {

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
						new GiftTabListener<SearchAllFragment>(this, "album",
								SearchAllFragment.class));
		actionBar.addTab(tab);

		tab = actionBar
				.newTab()
				.setText(R.string.gift_female)
				.setTabListener(
						new GiftTabListener<SearchFemaleFragment>(this,
								"album", SearchFemaleFragment.class));
		actionBar.addTab(tab);

		tab = actionBar
				.newTab()
				.setText(R.string.gift_male)
				.setTabListener(
						new GiftTabListener<SearchMaleFragment>(this, "album",
								SearchMaleFragment.class));
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
		SearchAgeFrament newFragment = new SearchAgeFrament();

		// transaction.add(0, newFragment);
		transaction.replace(android.R.id.content, newFragment);
		transaction.addToBackStack(null);
		// Commit the transaction
		transaction.commit();
	}

	public void searchAge(View view) {
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		SearchPurposeFrament newFragment = new SearchPurposeFrament();

		transaction.replace(android.R.id.content, newFragment);
		transaction.addToBackStack(null);
		// Commit the transaction
		transaction.commit();
	}

	public void searchPurpose(View view) {
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		SearchProductFrament newFragment = new SearchProductFrament();
		transaction.replace(android.R.id.content, newFragment);
		transaction.addToBackStack(null);
		// Commit the transaction
		transaction.commit();
	}

	public static class GiftTabListener<T extends Fragment> implements
			ActionBar.TabListener {
		private Fragment mFragment;
		private final Activity mActivity;
		private final String mTag;
		private final Class<T> mClass;

		public GiftTabListener(Activity activity, String tag, Class<T> clz) {
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

	public static class SearchAgeFrament extends Fragment {

		public SearchAgeFrament() {

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Utils.SetActionBar(this.getActivity(),
					getString(R.string.age_actionbar_title));
			View rootView = inflater.inflate(R.layout.fragment_search_age,
					container, false);
			return rootView;
		}
	}

	public static class SearchPurposeFrament extends Fragment {

		public SearchPurposeFrament() {

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Utils.SetActionBar(this.getActivity(),
					getString(R.string.purpose_actionbar_title));
			View rootView = inflater.inflate(R.layout.fragment_search_purpose,
					container, false);
			return rootView;
		}
	}

	public static class SearchProductFrament extends Fragment {
		ListView listview;
		LazyAdapter adapter;

		private ShareActionProvider mShareActionProvider;

		public SearchProductFrament() {

			final Activity activity = this.getActivity();
			listview = (ListView) activity.findViewById(R.id.listView1);
			adapter = new LazyAdapter(activity, imageUrls);
			listview.setAdapter(adapter);
			// listview.setAdapter(new ImageAdapter(this));

			listview.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					// TODO Auto-generated method stub
					Toast.makeText(activity.getApplicationContext(),
							"" + position, Toast.LENGTH_SHORT).show();
				}

			});
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
			View rootView = inflater.inflate(R.layout.fragment_search_product,
					container, false);
			return rootView;
		}
	}
}
