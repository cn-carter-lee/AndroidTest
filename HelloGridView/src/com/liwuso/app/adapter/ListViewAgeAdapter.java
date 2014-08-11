package com.liwuso.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.liwuso.app.R;
import com.pys.liwuso.bean.Age;

public class ListViewAgeAdapter extends BaseAdapter {

	private Context context;
	private List<Age> listItems;
	private LayoutInflater listContainer;

	private int[] itemViewResourceArray = { R.anim.age_lisitem0,
			R.anim.age_lisitem1, R.anim.age_lisitem2, R.anim.age_lisitem3,
			R.anim.age_lisitem4, };

	static class ListItemView {
		public Button button;
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
		ListItemView listItemView = null;
		if (convertView == null) {
			convertView = listContainer.inflate(itemViewResourceArray[position
					% itemViewResourceArray.length], null);
			listItemView = new ListItemView();
			listItemView.button = (Button) convertView
					.findViewById(R.id.btnAge);
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		Age age = listItems.get(position);
		listItemView.button.setText(age.Name);
		return convertView;
	}
}
