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
import com.liwuso.bean.Person;

public class ListViewMaleAdapter extends BaseAdapter {

	private Context context;
	private List<Person> listItems;
	private LayoutInflater listContainer;

	private int[] itemViewResourceArray = { R.anim.person_male_lisitem0,
			R.anim.person_male_lisitem1, R.anim.person_male_lisitem2,
			R.anim.person_male_lisitem3, R.anim.person_male_lisitem4, };

	static class CustomListItemView {
		public Button text;
	}

	public ListViewMaleAdapter(Context context, List<Person> data) {
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
		listItemView.text = (Button) convertView.findViewById(R.id.btnPerson);
		convertView.setTag(listItemView);

		Person person = listItems.get(position);
		listItemView.text.setText(person.Name);
		listItemView.text.setTag(person);
		return convertView;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

}
