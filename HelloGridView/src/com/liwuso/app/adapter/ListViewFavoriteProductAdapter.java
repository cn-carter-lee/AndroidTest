package com.liwuso.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liwuso.app.R;
import com.liwuso.utility.ImageLoader;
import com.pys.liwuso.bean.Product;

public class ListViewFavoriteProductAdapter extends BaseAdapter {

	private Context context;
	private List<Product> listItems;
	private LayoutInflater listContainer;
	public ImageLoader imageLoader;

	static class ListItemView {
		public TextView title;
		public TextView price;
		public TextView number;
		public ImageView image;
	}

	public ListViewFavoriteProductAdapter(Context context, List<Product> data) {
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
		ListItemView listItemView = null;

		if (convertView == null) {
			convertView = listContainer.inflate(R.layout.favorite_item, null);

			listItemView = new ListItemView();
			listItemView.title = (TextView) convertView
					.findViewById(R.id.favorite_product_title);
			listItemView.price = (TextView) convertView
					.findViewById(R.id.favorite_product_price);

			listItemView.number = (TextView) convertView
					.findViewById(R.id.favorite_product_number);

			listItemView.image = (ImageView) convertView
					.findViewById(R.id.favorite_product_image);
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		Product product = listItems.get(position);

		listItemView.title.setText(product.Name);
		listItemView.price.setText(product.Price);
		listItemView.number.setText("1020»À ’≤ÿ");
		imageLoader.DisplayImage(product.ImageUrl, listItemView.image);
		return convertView;
	}
}
