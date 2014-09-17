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

	public SearchAdapter(Context context, List<SearchItemWapper> data) {
		this.context = context;
		this.listContainer = LayoutInflater.from(context);
		this.listItemWappers = data;
		this.imageLoader = new ImageLoader(context);
	}

	static class CustomListItemView {
		public TextView name;
		public ImageView image;
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
		SearchItemWapper searchItem = listItemWappers.get(position);
		LinearLayout row = (LinearLayout) listContainer.inflate(
				R.layout.search_product_row, null);

		for (int i = 0; i < 3; i++) {
			CustomListItemView listItemView = new CustomListItemView();
			convertView = listContainer.inflate(R.layout.search_product_item,
					null);
			listItemView.name = (TextView) convertView.findViewById(R.id.name);
			listItemView.image = (ImageView) convertView
					.findViewById(R.id.image);

			listItemView.name.setText(searchItem.Items[i].Name);
			listItemView.name.setTag(searchItem);
			imageLoader.DisplayImage(searchItem.Items[i].ImageUrl, listItemView.image);
			listItemView.image.setTag(searchItem);
			row.addView(convertView);
		}
		return row;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}
