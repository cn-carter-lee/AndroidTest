package com.liwuso.app.adapter;

import com.liwuso.app.R;
import com.liwuso.utility.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private Activity activity;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;

	public ImageAdapter(Activity a, Context c) {
		activity = a;
		mContext = c;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	public int getCount() {
		return mThumbIds.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item reference by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.search_product_item, null);
		ImageView imageView = (ImageView) vi.findViewById(R.id.image);
		imageView.setImageResource(mThumbIds[position]);
		return vi;

	}

	// reference to our images
	private Integer[] mThumbIds = { R.drawable.sample_0, R.drawable.sample_0,
			R.drawable.sample_0, R.drawable.sample_0, R.drawable.sample_0,
			R.drawable.sample_0, R.drawable.sample_0, R.drawable.sample_0,
			R.drawable.sample_0, R.drawable.sample_0, R.drawable.sample_0,
			R.drawable.sample_0, R.drawable.sample_0, R.drawable.sample_0,
			R.drawable.sample_0, R.drawable.sample_0, R.drawable.sample_0,
			R.drawable.sample_0, R.drawable.sample_0, R.drawable.sample_0,
			R.drawable.sample_0, R.drawable.sample_0

	};
}
