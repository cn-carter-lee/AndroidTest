package com.pys.hellogridview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pys.hellogridview.ImageLoader;

public class LazyAdapter extends BaseAdapter {

	private Activity activity;
	private String[] data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;

	public LazyAdapter(Activity a, String[] d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	public int getCount() {
		return data.length;
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
			vi = inflater.inflate(R.layout.so_product_item, null);

		TextView text = (TextView) vi.findViewById(R.id.text);
		ImageView image = (ImageView) vi.findViewById(R.id.image);
		text.setText(position+"111���ղ�"  );
		imageLoader.DisplayImage(data[position], image);
		return vi;
	}
}
