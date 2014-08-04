package com.liwuso.ui;

import java.util.ArrayList;

import com.liwuso.utility.LazyAdapter;
import com.liwuso.app.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;

public class FavoriteFrament extends Fragment {

	public FavoriteFrament() {
	}

	ListView listview;
	LazyAdapter adapter;
	ArrayList<SortItem> sortItems;

	private ShareActionProvider mShareActionProvider;

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
		// Utils.SetActionBar(this.getActivity(),getString(R.string.product_actionbar_title));
		View rootView = inflater.inflate(R.layout.fragment_favorite_product,
				container, false);
		super.onCreate(savedInstanceState);
		// Populate images info
		final Activity activity = this.getActivity();
		listview = (ListView) rootView.findViewById(R.id.listViewProduct);
		adapter = new LazyAdapter(activity, imageUrls);
		listview.setAdapter(adapter);
		return rootView;
	}

	public ArrayList<SortItem> populateList() {
		ArrayList<SortItem> items = new ArrayList<SortItem>();
		items.add(new SortItem("按综合排序", 0, R.drawable.ic_favorite));
		items.add(new SortItem("按价格排序", 1, R.drawable.ic_favorite));
		items.add(new SortItem("按人气排序", 2, R.drawable.ic_favorite));
		return items;
	}

}
