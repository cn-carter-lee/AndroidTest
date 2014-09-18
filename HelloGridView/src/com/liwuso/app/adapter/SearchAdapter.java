package com.liwuso.app.adapter;

import java.util.List;

import com.liwuso.app.R;
import com.liwuso.app.adapter.ListViewProductAdapter.CustomListItemView;
import com.liwuso.bean.Product;
import com.liwuso.bean.SearchItem;
import com.liwuso.bean.SearchItemListWapper;
import com.liwuso.bean.SearchItemWapper;
import com.liwuso.utility.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SearchAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater listContainer;;
	private List<SearchItemWapper> listItemWappers;
	public ImageLoader imageLoader;
	private OnClickListener ml;

	private int[] name_resource_ids = { R.id.name0, R.id.name1, R.id.name2 };
	private int[] image_resource_ids = { R.id.image0, R.id.image1, R.id.image2 };

	public SearchAdapter(Context context, List<SearchItemWapper> data) {
		this.context = context;
		this.listContainer = LayoutInflater.from(context);
		this.listItemWappers = data;
		this.imageLoader = new ImageLoader(context);
	}

	static class CustomListItemView {
		public TextView[] names = new TextView[3];
		public ImageView[] images = new ImageView[3];
	}

	public int getCount() {
		return listItemWappers.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item reference by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		SearchItemWapper searchItemWapper = listItemWappers.get(position);
		convertView = (LinearLayout) listContainer.inflate(
				R.layout.search_product_row, null);
		CustomListItemView listItemView = new CustomListItemView();
		for (int i = 0; i < 3; i++) {
			SearchItem searchItem = searchItemWapper.Items[i];
			if (searchItem != null) {
				listItemView.names[i] = (TextView) convertView
						.findViewById(name_resource_ids[i]);
				listItemView.images[i] = (ImageView) convertView
						.findViewById(image_resource_ids[i]);

				listItemView.names[i].setText(searchItem.Name);
				listItemView.images[i].setTag(searchItem);
				imageLoader.DisplayImage(searchItem.ImageUrl,
						listItemView.images[i]);
				listItemView.images[i].setTag(searchItem);
			}
		}
		return convertView;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}
