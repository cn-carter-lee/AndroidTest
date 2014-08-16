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
import com.pys.liwuso.bean.Aim;

public class ListViewAimAdapter extends BaseAdapter {

	private Context context;
	private List<Aim> listItems;
	private LayoutInflater listContainer;

	private int[] itemViewResourceArray = { R.anim.purpose_lisitem0,
			R.anim.purpose_lisitem1, R.anim.purpose_lisitem2,
			R.anim.purpose_lisitem3, R.anim.purpose_lisitem4, };

	static class ListItemView {
		public TextView text;
	}

	public ListViewAimAdapter(Context context, List<Aim> data) {
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
			listItemView.text = (TextView) convertView
					.findViewById(R.id.btnPurpose);
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		Aim purpose = listItems.get(position);

		listItemView.text.setText(purpose.Name);
		return convertView;
	}
}
