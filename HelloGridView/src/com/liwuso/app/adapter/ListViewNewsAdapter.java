package com.liwuso.app.adapter;

import java.util.List;

import com.pys.liwuso.bean.Product;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewNewsAdapter extends BaseAdapter {
	private List<Product> listItems;// 数据集合

	public ListViewNewsAdapter(Context context, List<Product> data, int resource) {
		this.listItems = data;
	}

	public int getCount() {
		return listItems.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int arg0) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// Log.d("method", "getView");

		return null;
	}
}
