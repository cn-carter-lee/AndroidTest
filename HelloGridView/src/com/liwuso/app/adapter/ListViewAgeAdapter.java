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
import com.liwuso.bean.Age;

public class ListViewAgeAdapter extends BaseAdapter {

	private Context context;
	private List<Age> listItems;
	private LayoutInflater listContainer;

	private int[] itemViewResourceArray = { R.anim.age_lisitem0,
			R.anim.age_lisitem1, R.anim.age_lisitem2, R.anim.age_lisitem3,
			R.anim.age_lisitem4, };

	static class CustomListItemView {
		public Button text;
	}

	public ListViewAgeAdapter(Context context, List<Age> data) {
		this.context = context;
		this.listContainer = LayoutInflater.from(context);
		this.listItems = data;
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

		convertView = listContainer.inflate(itemViewResourceArray[position
				% itemViewResourceArray.length], null);
		listItemView = new CustomListItemView();
		listItemView.text = (Button) convertView.findViewById(R.id.btnAge);
		convertView.setTag(listItemView);

		Age age = listItems.get(position);
		listItemView.text.setText(age.Name);
		listItemView.text.setTag(age);
		return convertView;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

}
