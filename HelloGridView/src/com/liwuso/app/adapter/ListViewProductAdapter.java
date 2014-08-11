package com.liwuso.app.adapter;

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
import com.pys.liwuso.bean.Person;
import com.pys.liwuso.bean.Product;

public class ListViewProductAdapter extends BaseAdapter {

	private Context context;
	private List<Product> listItems;
	private LayoutInflater listContainer;

	private int[] itemViewResourceArray = { R.anim.person_male_lisitem0,
			R.anim.person_male_lisitem1, R.anim.person_male_lisitem2,
			R.anim.person_male_lisitem3, R.anim.person_male_lisitem4, };
	public ImageLoader imageLoader;

	static class ListItemView {
		public TextView text;
		public TextView price;
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
		ListItemView listItemView = null;

		if (convertView == null) {

			convertView = listContainer.inflate(R.layout.search_item, null);

			listItemView = new ListItemView();
			listItemView.text = (TextView) convertView.findViewById(R.id.title);
			listItemView.price = (TextView) convertView
					.findViewById(R.id.price);
			listItemView.image = (ImageView) convertView
					.findViewById(R.id.image);
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		Product product = listItems.get(position);

		listItemView.text.setText(product.Name);
		listItemView.price.setText(product.Price);
		imageLoader.DisplayImage(product.ImageUrl, listItemView.image);

		return convertView;
	}
}
