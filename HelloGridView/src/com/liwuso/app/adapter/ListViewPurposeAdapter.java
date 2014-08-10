package com.liwuso.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import com.liwuso.app.R;
import com.pys.liwuso.bean.Purpose;

public class ListViewPurposeAdapter extends BaseAdapter {

	private Context context;
	private List<Purpose> listItems;
	private LayoutInflater listContainer;

	private int[] itemViewResourceArray = { R.anim.purpose_lisitem0,
			R.anim.purpose_lisitem1, R.anim.purpose_lisitem2,
			R.anim.purpose_lisitem3, R.anim.purpose_lisitem4, };

	static class ListItemView {
		public Button button;
	}

	public ListViewPurposeAdapter(Context context, List<Purpose> data) {
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
					.findViewById(R.id.btnPurpose);
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		Purpose purpose = listItems.get(position);

		listItemView.button.setText(purpose.Name);
		return convertView;
	}
}
