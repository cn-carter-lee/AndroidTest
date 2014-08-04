package com.liwuso.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;

import com.liwuso.utility.LazyAdapter;
import com.liwuso.app.R;
import com.liwuso.helper.SortAdapter;
import com.liwuso.helper.SortItem;

public class FragmentProduct extends BaseFragment {
	ListView listview;
	LazyAdapter adapter;
	ArrayList<SortItem> sortItems;

	public FragmentProduct() {

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
		this.setNavgationTitle("礼物推荐");
		View rootView = inflater.inflate(R.layout.fragment_so_product,
				container, false);
		super.onCreate(savedInstanceState);
		// Populate images info
		final Activity activity = this.getActivity();
		listview = (ListView) rootView.findViewById(R.id.listViewProduct);
		adapter = new LazyAdapter(activity, imageUrls);
		listview.setAdapter(adapter);
		// Populate spinner
		Spinner spinner = (Spinner) this.getActivity().findViewById(
				R.id.search_sorter);

		sortItems = populateList();

		SortAdapter myAdapter = new SortAdapter(this.getActivity(),
				android.R.layout.simple_spinner_item, sortItems);
		spinner.setAdapter(myAdapter);
		spinner.setVisibility(View.VISIBLE);
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
