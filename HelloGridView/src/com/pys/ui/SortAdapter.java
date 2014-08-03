package com.pys.ui;

import java.util.ArrayList;

import com.pys.hellogridview.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SortAdapter extends ArrayAdapter<SortItem> {
	private Activity context;
	ArrayList<SortItem> data = null;

	public SortAdapter(Activity context, int resource, ArrayList<SortItem> data) {
		super(context, resource, data);
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Ordinary view in Spinner,we use android.R.layout.simple_spinner_item

		// final LayoutInflater inflater = context.getLayoutInflater();

		// final View spinnerEntry =
		// inflater.inflate(R.layout.spinner_entry_with_icon, null);
		// initialize the layout from xml
		// final View spinnerEntry = inflater.inflate(R.layout.spinner_entry,
		// null)
		final LayoutInflater inflater = context.getLayoutInflater();
		final View spinnerEntry = inflater
				.inflate(R.layout.spinner_sorter, null);
		return spinnerEntry;

		// return super.getView(position, convertView, parent);
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// This view starts when we click the spinner.
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			row = inflater.inflate(R.layout.spinner_sorter_item, parent, false);
		}

		SortItem item = data.get(position);

		if (item != null) { // Parse the data from each object and set it.
			// ImageView myFlag = (ImageView) row.findViewById(R.id.item_icon);
			TextView textView = (TextView) row.findViewById(R.id.item_name);
			// if (myFlag != null) {
			// myFlag.setBackgroundDrawable(getResources().getDrawable(
			// item.getCountryFlag()));
			// }
			if (textView != null)
				textView.setText(item.getItemName());

		}
		return row;
	}
}
