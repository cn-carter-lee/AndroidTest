package com.liwuso.app.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.liwuso.app.R;
import com.liwuso.utility.ImageLoader;
import com.pys.liwuso.bean.Person;
import com.pys.liwuso.bean.PersonList;

public class PersonAdapter extends BaseAdapter {

	private Activity activity;
	private List<Person> data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;

	public PersonAdapter(Activity activity, List<Person> data) {
		this.activity = activity;
		this.data = data;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	public int getCount() {
		return this.data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.person_item, null);

		Button button = (Button) vi.findViewById(R.id.btnPerson);
		button.setText("YYYYYY");
		// ImageView image = (ImageView) vi.findViewById(R.id.image);
		// text.setText(position+"111“— ’≤ÿ" );
		// imageLoader.DisplayImage(data[position], image);
		return vi;
	}
}
