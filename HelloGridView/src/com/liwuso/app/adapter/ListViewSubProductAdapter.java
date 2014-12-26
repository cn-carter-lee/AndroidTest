package com.liwuso.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.liwuso.app.R;
import com.liwuso.app.adapter.ListViewProductAdapter.CustomListItemView;
import com.liwuso.app.widget.CustomImageView;
import com.liwuso.app.widget.WordWrapView;
import com.liwuso.bean.Product;
import com.liwuso.utility.ImageLoader;

public class ListViewSubProductAdapter extends BaseAdapter {

	private Context context;
	private List<Product> listItems;
	private LayoutInflater listContainer;

	public ImageLoader imageLoader;

	static class CustomListItemView {
		public TextView name;
		public TextView price;
		public CustomImageView image;
	}

	public ListViewSubProductAdapter(Context context, List<Product> data) {

		this.context = context;
		this.listContainer = LayoutInflater.from(context);
		this.listItems = data;
		this.imageLoader = new ImageLoader(context);
	}

	public int getCount() {
		return listItems.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		CustomListItemView listItemView = null;
		convertView = listContainer.inflate(R.layout.product_sub_item, parent,
				false);
		listItemView = new CustomListItemView();
		listItemView.name = (TextView) convertView.findViewById(R.id.name);
		listItemView.price = (TextView) convertView.findViewById(R.id.price);
		listItemView.image = (CustomImageView) convertView
				.findViewById(R.id.image);

		convertView.setTag(listItemView);

		Product product = listItems.get(position);
		listItemView.name.setText(product.Name);
		listItemView.name.setTag(product);

		listItemView.price.setText("гд" + product.Price);
		imageLoader.DisplayImage(product.ImageUrl, listItemView.image);
		listItemView.image.setTag(product);

		return convertView;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}
