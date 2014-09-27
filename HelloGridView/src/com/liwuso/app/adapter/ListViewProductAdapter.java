package com.liwuso.app.adapter;

import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liwuso.app.R;
import com.liwuso.app.widget.WordWrapView;
import com.liwuso.bean.Product;
import com.liwuso.utility.ImageLoader;
import com.liwuso.utility.ScreenUtils;
import com.liwuso.utility.Utils;

public class ListViewProductAdapter extends BaseAdapter {

	private Context context;
	private List<Product> listItems;
	private LayoutInflater listContainer;

	public ImageLoader imageLoader;

	static class CustomListItemView {
		public TextView name;
		public WordWrapView tags;
		public TextView price;
		public Button favorite;
		public Button no_favorite;
		public Button details;
		public ImageView image;
	}

	public ListViewProductAdapter(Context context, List<Product> data) {

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
		convertView = listContainer.inflate(R.layout.product_item, parent,
				false);
		listItemView = new CustomListItemView();
		listItemView.name = (TextView) convertView.findViewById(R.id.name);
		listItemView.tags = (WordWrapView) convertView.findViewById(R.id.tags);
		listItemView.price = (TextView) convertView.findViewById(R.id.price);
		listItemView.favorite = (Button) convertView
				.findViewById(R.id.btn_favorite);
		listItemView.no_favorite = (Button) convertView
				.findViewById(R.id.btn_no_favorite);
		listItemView.details = (Button) convertView
				.findViewById(R.id.btn_details);
		listItemView.image = (ImageView) convertView.findViewById(R.id.image);

		convertView.setTag(listItemView);

		Product product = listItems.get(position);
		if (Utils.getFavoriteList().contains(String.valueOf(product.getId()))) {
			listItemView.favorite.setVisibility(View.VISIBLE);
			listItemView.no_favorite.setVisibility(View.GONE);
		} else {
			listItemView.favorite.setVisibility(View.GONE);
			listItemView.no_favorite.setVisibility(View.VISIBLE);
		}
		listItemView.favorite.setTag(product);
		listItemView.no_favorite.setText(product.Liked + "ÒÑÊÕ²Ø");
		listItemView.no_favorite.setTag(product);
		listItemView.details.setTag(product);
		listItemView.name.setText(product.Name);
		listItemView.name.setTag(product);

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
		listItemView.price.setText("£¤" + product.Price);
		imageLoader.DisplayImage(product.ImageUrl, listItemView.image);
		listItemView.image.setTag(product);

		Activity a = (Activity) context;
		int w = (int) ScreenUtils.getWidth(a);

		android.widget.AbsListView.LayoutParams layoutParams = new android.widget.AbsListView.LayoutParams(
				w, 200);
		// LinearLayout.LayoutParams layoutParams = new
		// LinearLayout.LayoutParams(100, 100);
		// convertView.setLayoutParams(layoutParams);
		ViewGroup.LayoutParams params = convertView.getLayoutParams();
		if (params != null)
			params.height = 200;
		// final LayoutParams params = convertView.getLayoutParams();
		// convertView.getLayoutParams().height = 200;

		return convertView;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}
