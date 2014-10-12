package com.liwuso.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.liwuso.app.R;
import com.liwuso.app.widget.WordWrapView;
import com.liwuso.bean.Product;
import com.liwuso.utility.ImageLoader;

public class ListViewFavoriteAdapter extends BaseAdapter {

	private Context context;
	private List<Product> listItems;
	private LayoutInflater listContainer;
	public ImageLoader imageLoader;

	static class CustomListItemView {
		public TextView name;
		public WordWrapView tags;
		public TextView price;
		public TextView number;
		public Button favorite;
		public Button details;
		public ImageView image;
	}

	public ListViewFavoriteAdapter(Context context, List<Product> data) {
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
		convertView = listContainer.inflate(R.layout.favorite_item, null);
		listItemView = new CustomListItemView();
		listItemView.name = (TextView) convertView
				.findViewById(R.id.favorite_product_name);
		listItemView.tags = (WordWrapView) convertView.findViewById(R.id.tags);

		listItemView.price = (TextView) convertView
				.findViewById(R.id.favorite_product_price);

		listItemView.image = (ImageView) convertView.findViewById(R.id.image);

		listItemView.favorite = (Button) convertView
				.findViewById(R.id.btn_favorite);
		listItemView.favorite.setTag(position);

		listItemView.details = (Button) convertView
				.findViewById(R.id.btn_favorite_details);
		convertView.setTag(listItemView);

		Product product = listItems.get(position);
		listItemView.name.setText(product.Name);
		listItemView.name.setTag(product);
		listItemView.price.setText("гд" + product.Price);
		listItemView.details.setTag(product);
		imageLoader.DisplayImage(product.ImageUrl, listItemView.image);
		listItemView.image.setTag(product);

		if (product.Tags.length() > 0) {
			String[] tagArray = product.Tags.split(",");
			for (int i = 0; i < tagArray.length; i++) {
				View product_item_tagview = listContainer.inflate(
						R.layout.product_item_tag, null);
				TextView txtTagView = (TextView) product_item_tagview
						.findViewById(R.id.product_tag);
				txtTagView.setText(tagArray[i]);
				listItemView.tags.addView(product_item_tagview);
			}
		}

		imageLoader.DisplayImage(product.ImageUrl, listItemView.image);
		listItemView.image.setTag(product);
		return convertView;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}
