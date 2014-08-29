package com.liwuso.app.adapter;

import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Text;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.liwuso.app.R;
import com.liwuso.utility.ImageLoader;
import com.liwuso.utility.Utils;
import com.pys.liwuso.bean.Person;
import com.pys.liwuso.bean.Product;

public class ListViewProductAdapter extends BaseAdapter {

	private Context context;
	private List<Product> listItems;
	private LayoutInflater listContainer;

	public ImageLoader imageLoader;

	static class CustomListItemView {
		public TextView name;
		public TextView price;
		public TextView favorite;
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

		String[] favoriteArray = Utils.getFavoriteArray();

		convertView = listContainer.inflate(R.layout.search_item, null);
		listItemView = new CustomListItemView();
		listItemView.name = (TextView) convertView.findViewById(R.id.name);
		listItemView.price = (TextView) convertView.findViewById(R.id.price);
		listItemView.favorite = (TextView) convertView
				.findViewById(R.id.favorite_text);
		listItemView.image = (ImageView) convertView.findViewById(R.id.image);

		convertView.setTag(listItemView);

		Product product = listItems.get(position);
		if (Arrays.asList(favoriteArray).contains(
				String.valueOf(product.getId()))) {
			listItemView.favorite.setText("已收藏");
			listItemView.favorite.setEnabled(false);
		} else {
			listItemView.favorite.setText("1002已收藏");
			listItemView.favorite.setEnabled(true);		
		}
		listItemView.name.setText(product.Name);
		listItemView.price.setText(product.Price);
		imageLoader.DisplayImage(product.ImageUrl, listItemView.image);
		return convertView;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}
