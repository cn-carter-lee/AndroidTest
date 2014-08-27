package com.liwuso.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.liwuso.app.R;
import com.pys.liwuso.bean.MixedPerson;

public class ListViewPersonAdapter extends BaseAdapter {

	private Context context;
	private List<MixedPerson> listItems;
	private LayoutInflater listContainer;

	private int[] itemViewResourceArray = { R.anim.person_lisitem0,
			R.anim.person_lisitem1, R.anim.person_lisitem2,
			R.anim.person_lisitem3, R.anim.person_lisitem4 };
	public final static int SEXTYPE_FEMALE = 0X00;
	public final static int SEXTYPE_MALE = 0X01;

	static class ListItemView {
		public TextView text1;
		public TextView text2;
	}

	public ListViewPersonAdapter(Context context, List<MixedPerson> data) {
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
			listItemView.text1 = (TextView) convertView
					.findViewById(R.id.btnPerson1);
			listItemView.text2 = (TextView) convertView
					.findViewById(R.id.btnPerson2);
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		MixedPerson mixedPerson = listItems.get(position);

		listItemView.text1.setText(mixedPerson.female.Name);
		listItemView.text2.setText(mixedPerson.male.Name);
		return convertView;
	}
}